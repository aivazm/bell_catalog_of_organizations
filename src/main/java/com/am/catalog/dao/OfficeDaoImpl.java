package com.am.catalog.dao;

import com.am.catalog.dto.OfficeRs;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.exception.NotUniqueException;
import com.am.catalog.model.Office;
import com.am.catalog.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfficeDaoImpl implements OfficeDao {

    private final EntityManager em;

    @Autowired
    public OfficeDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Office saveOff(Office office) {
        getUniqueOffice(office.getName(), office.getOrganization());
        em.persist(office);
        return office;
    }

    @Override
    public Office updateOff(Office office) {
        Office o = em.find(Office.class, office.getId());
        if (o != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<Office> update = cb.createCriteriaUpdate(Office.class);
            Root<Office> root = update.from(Office.class);
            update.set(root.get("name"), office.getName());
            update.set(root.get("address"), office.getAddress());
            if (office.getPhone() != null) {
                update.set(root.get("phone"), office.getPhone());
            }
            update.set(root.get("isActive"), office.isActive());
            if (office.getOrganization() != null) {
                getUniqueOffice(office.getName(), office.getOrganization());
                update.set(root.get("organization"), office.getOrganization());
            } else {
                getUniqueOffice(office.getName(), o.getOrganization());
            }
            update.where(root.get("id").in(office.getId()));
            Query query = em.createQuery(update);
            query.executeUpdate();
            return office;
        } else {
            throw new NoObjectException("Нет офиса с id: " + office.getId());
        }
    }

    @Override
    public OfficeRs findOffById(Long id) {
        Office o = em.find(Office.class, id);
        if (o == null) {
            throw new NoObjectException("Нет офиса с id: " + id);
        }
        return new OfficeRs(o.getId(), o.getName(), o.getAddress(), o.getPhone(), o.isActive());
    }

    @Override
    public List<OfficeRs> getOffList(Organization org, String name, String phone, Boolean isActive) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Office> criteriaQuery = criteriaBuilder.createQuery(Office.class);
        Root<Office> root = criteriaQuery.from(Office.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("organization"), org));
        if (name != null && !name.equals("")) {
            predicates.add(criteriaBuilder.equal(root.get("name"), name));
        }
        if (phone != null && !phone.equals("")) {
            predicates.add(criteriaBuilder.equal(root.get("phone"), phone));
        }
        if (isActive != null) {
            predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
        }
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        List<Office> offices = em.createQuery(criteriaQuery).getResultList();
        if (offices.isEmpty()) {
            throw new NoObjectException("Офисы, удовлетворяющие параметрам, отсутствуют");
        }
        List<OfficeRs> listOffRs = new ArrayList<>();
        for (Office o : offices) {
            OfficeRs offRs = new OfficeRs(o.getId(), o.getName(), o.isActive());
            listOffRs.add(offRs);
        }
        return listOffRs;
    }


    private void getUniqueOffice(String offName, Organization org) {
        final String FIND_BY_NAME_QUERY = "SELECT o FROM Office o WHERE o.name = :name AND o.organization = :organization";
        TypedQuery<Office> query = em.createQuery(FIND_BY_NAME_QUERY, Office.class);
        query.setParameter("name", offName);
        query.setParameter("organization", org);
        List<Office> offList = query.getResultList();
        if (!offList.isEmpty()) {
            throw new NotUniqueException("В организации с id " + org.getId() + " офис с именем " + offName + " уже существует");
        }
    }
}
