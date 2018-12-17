package com.am.catalog.dao;

import com.am.catalog.exception.NoObjectException;
import com.am.catalog.exception.NotUniqueException;
import com.am.catalog.model.Country;
import com.am.catalog.model.DocType;
import com.am.catalog.model.Document;
import com.am.catalog.model.Office;
import com.am.catalog.model.User;
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
public class UserDaoImpl implements UserDao {
    private final EntityManager em;

    public UserDaoImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveUser(User user) {
        if (user.getDocument() != null) {
            Document userDocument = getUniqueDocument(user.getDocument());
            user.setDocument(userDocument);
        }
        if (user.getCountry() != null) {
            Country userCountry = getExistingCountry(user.getCountry());
            user.setCountry(userCountry);
        }
        em.persist(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateUser(User user, Long id) {
        if (em.find(User.class, id) != null) {
            Document oldDocument = em.find(User.class, id).getDocument();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaUpdate<User> update = cb.createCriteriaUpdate(User.class);
            Root<User> root = update.from(User.class);
            update.set(root.get("firstName"), user.getFirstName());
            update.set(root.get("position"), user.getPosition());
            if (user.getSecondName() != null) {
                update.set(root.get("secondName"), user.getSecondName());
            }
            if (user.getMiddleName() != null) {
                update.set(root.get("middleName"), user.getMiddleName());
            }
            if (user.getPhone() != null) {
                update.set(root.get("phone"), user.getPhone());
            }
            if (user.getDocument() != null) {
                update.set(root.get("document"), getUniqueDocument(user.getDocument()));
            }
            if (user.getCountry() != null) {
                update.set(root.get("country"), getExistingCountry(user.getCountry()));
            }
            if (user.isIdentified() != null) {
                update.set(root.get("isIdentified"), user.isIdentified());
            }
            update.where(root.get("id").in(id));
            Query query = em.createQuery(update);
            if (user.getDocument() != null && oldDocument != null) {
                em.remove(oldDocument);
            }
            return (query.executeUpdate());
        } else {
            throw new NoObjectException("Нет работника с id: " + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserById(Long id) {
        User user = em.find(User.class, id);
        if (user == null) {
            throw new NoObjectException("Нет работника с id: " + id);
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUserList(Long officeId,
                                  String firstName,
                                  String secondName,
                                  String middleName,
                                  String position,
                                  String docCode,
                                  String citizenshipCode
    ) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("office"), em.find(Office.class, officeId)));
        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("firstName"), firstName));
        }
        if (secondName != null && !secondName.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("secondName"), secondName));
        }
        if (middleName != null && !middleName.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("middleName"), middleName));
        }
        if (position != null && !position.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("position"), position));
        }
        if (citizenshipCode != null && !citizenshipCode.isEmpty()) {
            final String FIND_COUNTRY_QUERY = "SELECT c FROM Country c WHERE c.code = :code";
            TypedQuery<Country> query = em.createQuery(FIND_COUNTRY_QUERY, Country.class);
            query.setParameter("code", citizenshipCode);
            List<Country> countryList = query.getResultList();
            Country country;
            if (countryList.isEmpty()) {
                country = null;
            } else {
                country = countryList.get(0);
            }
            predicates.add(criteriaBuilder.equal(root.get("country"), country));
        }
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

        List<User> tempUserList = em.createQuery(criteriaQuery).getResultList();
        if (tempUserList.isEmpty()) {
            throw new NoObjectException("Работники, удовлетворяющие параметрам, отсутствуют");
        }
        if (docCode == null || docCode.isEmpty()) {
            return tempUserList;
        }
        List<User> users = new ArrayList<>();
        for (User u : tempUserList) {
            if (u.getDocument().getDocType().getCode().equals(docCode)) {
                users.add(u);
            }
        }
        if (users.isEmpty()) {
            throw new NoObjectException("Работники, удовлетворяющие параметрам, отсутствуют");
        }
        return users;
    }

    private Document getUniqueDocument(Document document) {
        final String FIND_DOC_TYPE_QUERY;
        TypedQuery<DocType> queryDocType;
        DocType docType;
        if (document.getDocType().getCode() != null) {
            FIND_DOC_TYPE_QUERY = "SELECT d FROM DocType d WHERE d.code = :code AND d.name = :name";
            queryDocType = em.createQuery(FIND_DOC_TYPE_QUERY, DocType.class);
            queryDocType.setParameter("code", document.getDocType().getCode());
            queryDocType.setParameter("name", document.getDocType().getName());
        } else {
            FIND_DOC_TYPE_QUERY = "SELECT d FROM DocType d WHERE d.name = :name";
            queryDocType = em.createQuery(FIND_DOC_TYPE_QUERY, DocType.class);
            queryDocType.setParameter("name", document.getDocType().getName());
        }
        List<DocType> docTypeList = queryDocType.getResultList();
        if (docTypeList.isEmpty()) {
            throw new NoObjectException("По указанным параметрам тип документа не установлен;");
        } else {
            docType = docTypeList.get(0);
        }
        final String FIND_DOC_QUERY = "SELECT d FROM Document d WHERE d.docType = :docType AND d.number = :number";
        TypedQuery<Document> queryDoc = em.createQuery(FIND_DOC_QUERY, Document.class);
        queryDoc.setParameter("docType", docType);
        queryDoc.setParameter("number", document.getNumber());
        List<Document> documentList = queryDoc.getResultList();
        if (!documentList.isEmpty()) {
            throw new NotUniqueException("Работник с указанным документом уже существует; ");
        }
        Document checkedDocument = new Document(docType, document.getNumber(), document.getDate());
        em.persist(checkedDocument);
        return checkedDocument;

    }

    private Country getExistingCountry(Country country) {
        final String FIND_COUNTRY_QUERY = "SELECT c FROM Country c WHERE c.code = :code";
        TypedQuery<Country> query = em.createQuery(FIND_COUNTRY_QUERY, Country.class);
        query.setParameter("code", country.getCode());
        List<Country> countryList = query.getResultList();
        if (countryList.isEmpty()) {
            throw new NoObjectException("По указаному коду государство не установлено; ");
        }
        return countryList.get(0);
    }


}
