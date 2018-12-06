package com.am.catalog.service;

import com.am.catalog.dao.OrganizationDao;
import com.am.catalog.view.OrganizationView;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * {@inheritDoc}
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationDao dao;
    private final Validator validator;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao dao, Validator validator) {
        this.dao = dao;
        this.validator = validator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OrganizationView saveOrganization(OrganizationView organizationView) {
        Organization organization = getValidOrganization(organizationView);
        dao.saveOrganization(organization);
        return new OrganizationView("success");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OrganizationView updateOrganization(OrganizationView organizationView) {
        if (organizationView.getId() == null) {
            throw new EmptyFieldException("id cannot be empty;");
        }
        Organization organization = getValidOrganization(organizationView);
        organization.setId(organizationView.getId());
        dao.updateOrganization(organization);
        return new OrganizationView("success");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationView getOrganizationById(Long id) {
        if (id < 1) {
            throw new EmptyFieldException("Id cannot be empty or less than one");
        }
        Organization org = dao.getOrganizationById(id);
        OrganizationView view = new OrganizationView(
                org.getId(),
                org.getName(),
                org.getFullName(),
                org.getInn(),
                org.getKpp(),
                org.getAddress(),
                org.getPhone(),
                org.isActive()
        );
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrganizationView> getOrganizationList(String name, String inn, Boolean isActive) {
        if (name == null || name.equals("")) {
            throw new EmptyFieldException("Name cannot be empty");
        }
        List<Organization> organizations = dao.getOrganizationList(name, inn, isActive);

        List<OrganizationView> viewList = new ArrayList<>();
        for (Organization o : organizations) {
            OrganizationView view = new OrganizationView(o.getId(), o.getName(), o.isActive());
            viewList.add(view);
        }
        return viewList;
    }

    private Organization getValidOrganization(OrganizationView organizationView) {
        Set<ConstraintViolation<OrganizationView>> validate = validator.validate(organizationView);
        if (!validate.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation<OrganizationView> violation : validate) {
                message.append(violation.getMessage());
                message.append("; ");
            }
            throw new EmptyFieldException(message.toString().trim());
        }
        Organization organization = new Organization();
        organization.setName(organizationView.getName());
        organization.setFullName(organizationView.getFullName());
        organization.setInn(organizationView.getInn());
        organization.setKpp(organizationView.getKpp());
        organization.setAddress(organizationView.getAddress());
        if (organizationView.getPhone() != null) {
            organization.setPhone(organizationView.getPhone());
        }
        if (organizationView.isActive() != null) {
            organization.setActive(organizationView.isActive());
        } else {
            organization.setActive(false);
        }

        return organization;
    }
}
