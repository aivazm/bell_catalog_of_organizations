package com.am.catalog.dao;

import com.am.catalog.exception.NoObjectException;
import com.am.catalog.exception.NotUniqueException;
import com.am.catalog.model.Office;
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
public class OfficeDaoImpl implements OfficeDao {

    private final EntityManager em;

    @Autowired
    public OfficeDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void saveOffice(Office office) {
        getUniqueOffice(office.getName(), office.getOrganization());
        em.persist(office);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOffice(Office office, Long id) {
        Office o = em.find(Office.class, id);
        if (o != null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<Office> update = cb.createCriteriaUpdate(Office.class);
            Root<Office> root = update.from(Office.class);
            update.set(root.get("name"), office.getName());
            update.set(root.get("address"), office.getAddress());
            if (office.getPhone() != null) {
                update.set(root.get("phone"), office.getPhone());
            }
            if (office.isActive() != null) {
                update.set(root.get("isActive"), office.isActive());
            }
            if (office.getOrganization() != null) {
                getUniqueOffice(office.getName(), office.getOrganization());
                update.set(root.get("organization"), office.getOrganization());
            } else {
                getUniqueOffice(office.getName(), o.getOrganization());
            }
            update.where(root.get("id").in(id));
            Query query = em.createQuery(update);
            query.executeUpdate();
        } else {
            throw new NoObjectException("Нет офиса с id: " + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Office getOfficeById(Long id) {
        Office office = em.find(Office.class, id);
        if (office == null) {
            throw new NoObjectException("Нет офиса с id: " + id);
        }
        return office;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Office> getOfficeList(Organization org, String name, String phone, Boolean isActive) {

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
        } else {
            return offices;
        }
    }


    private void getUniqueOffice(String officeName, Organization org) {
        final String FIND_BY_NAME_QUERY = "SELECT o FROM Office o WHERE o.name = :name AND o.organization = :organization";
        TypedQuery<Office> query = em.createQuery(FIND_BY_NAME_QUERY, Office.class);
        query.setParameter("name", officeName);
        query.setParameter("organization", org);
        List<Office> offList = query.getResultList();
        if (!offList.isEmpty()) {
            throw new NotUniqueException("В организации с id " + org.getId() + " офис с именем " + officeName + " уже существует");
        }
    }
}
