package com.am.catalog.dao;

import com.am.catalog.exception.NoObjectException;
import com.am.catalog.exception.NotUniqueException;
import com.am.catalog.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDao {

    private final EntityManager em;

    @Autowired
    public OrganizationDaoImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveOrganization(Organization org) {
        checkUniqueInn(org);
        em.persist(org);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateOrganization(Organization org, Long id) {
        Organization o = em.find(Organization.class, id);
        if (o != null) {
            checkUniqueInn(org);
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<Organization> update = cb.createCriteriaUpdate(Organization.class);
            Root<Organization> root = update.from(Organization.class);
            update.set(root.get("name"), org.getName());
            update.set(root.get("fullName"), org.getFullName());
            update.set(root.get("inn"), org.getInn());
            update.set(root.get("kpp"), org.getKpp());
            update.set(root.get("address"), org.getAddress());
            if (org.getPhone() != null) {
                update.set(root.get("phone"), org.getPhone());
            }
            update.set(root.get("isActive"), org.isActive());
            update.where(root.get("id").in(id));
            Query query = em.createQuery(update);
            return (query.executeUpdate());
        } else {
            throw new NoObjectException("Нет организации с id: " + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Organization getOrganizationById(Long id) {
        Organization o = em.find(Organization.class, id);
        if (o == null) {
            throw new NoObjectException("Нет организации с id: " + id);
        }
        return o;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Organization> getOrganizationList(String name, String inn, Boolean isActive) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Organization> criteriaQuery = criteriaBuilder.createQuery(Organization.class);
        Root<Organization> root = criteriaQuery.from(Organization.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("name"), name));
        if (inn != null) {
            predicates.add(criteriaBuilder.equal(root.get("inn"), inn));
        }
        if (isActive != null) {
            predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
        }
        criteriaQuery.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        List<Organization> organizations = em.createQuery(criteriaQuery).getResultList();
        if (organizations.isEmpty()) {
            throw new NoObjectException("Организации, удовлетворяющие параметрам, отсутствуют");
        }
        return organizations;
    }

    private void checkUniqueInn(Organization org){
        final String FIND_BY_INN_QUERY = "SELECT o FROM Organization o WHERE o.inn = :inn";
        TypedQuery<Organization> query = em.createQuery(FIND_BY_INN_QUERY, Organization.class);
        query.setParameter("inn", org.getInn());
        List<Organization> orgList = query.getResultList();
        if (!orgList.isEmpty()) {
            throw new NotUniqueException("Организация с ИНН " + org.getInn() + " уже существует");
        }
    }
}
