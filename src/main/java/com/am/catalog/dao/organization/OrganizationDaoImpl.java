package com.am.catalog.dao.organization;

import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoOrganizationException;
import com.am.catalog.exception.NotUniqueException;
import com.am.catalog.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrganizationDaoImpl implements OrganizationDao {

    private final EntityManager em;

    @Autowired
    public OrganizationDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public String save (Organization org) {
        String queryString = "SELECT o FROM Organization o WHERE o.inn = :inn";
        TypedQuery<Organization> query = em.createQuery(queryString, Organization.class);
        query.setParameter("inn", org.getInn());
        List<Organization> orgList = query.getResultList();
        if (orgList.size() != 0){
            throw new NotUniqueException("Error:\nОрганизация с ИНН " + org.getInn() +" уже существует");
        } else {
            em.persist(org);
            return "Success";
        }
    }

    @Override
    public String update(Organization org) {
        Organization o = em.find(Organization.class, org.getId());
        if (o != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<Organization> update = cb.createCriteriaUpdate(Organization.class);
            Root<Organization> root = update.from(Organization.class);
            update.set(root.get("name"),org.getName());
            update.set(root.get("fullName"),org.getFullName());
            update.set(root.get("inn"),org.getInn());
            update.set(root.get("kpp"),org.getKpp());
            update.set(root.get("address"),org.getAddress());
            if (org.getPhone() != null) {
                update.set(root.get("phone"),org.getPhone());
            }
            update.set(root.get("isActive"),org.isActive());
            update.where(root.get("id").in(org.getId()));
            Query query = em.createQuery(update);
            query.executeUpdate();
            return "Success";
        } else {
            throw new NoOrganizationException("Error:\nНет организации с id: " + org.getId());
        }
    }

    @Override
    public Organization idOrg(Long id) {
        Organization o = em.find(Organization.class, id);
        if (o == null){
            throw new NoOrganizationException("Error:\nНет организации с id: " + id);
        }
        return o;
    }

    @Override
    public List<Organization> listOrg(String name, String inn, Boolean isActive) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
        Root<Organization> root = criteriaQuery.from(Organization.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("name"),name));
        if (inn != null) {
            predicates.add(criteriaBuilder.equal(root.get("inn"), inn));
        }
        if (isActive != null) {
            predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
        }
        criteriaQuery.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        List<Organization> organizations = em.createQuery(criteriaQuery).getResultList();
        if (organizations.size()==0){
            throw new NoOrganizationException("Error:\nОрганизации, удовлетворяющие параметрам, отсутствуют");
        }
        for(Organization o : organizations){
            o.setFullName(null);
            o.setAddress(null);
            o.setPhone(null);
            o.setInn(null);
            o.setKpp(null);
        }


        return organizations;
    }
}
