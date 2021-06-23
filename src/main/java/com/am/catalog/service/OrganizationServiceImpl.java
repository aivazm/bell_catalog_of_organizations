package com.am.catalog.service;

import com.am.catalog.dao.OrganizationDao;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.model.Organization;
import com.am.catalog.view.OrganizationView;
import com.am.catalog.view.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public SuccessResponse saveOrganization(OrganizationView organizationView) {
        Organization organization = getValidOrganization(organizationView);
        dao.saveOrganization(organization);
        return new SuccessResponse("success");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SuccessResponse updateOrganization(OrganizationView organizationView) {
        if (organizationView.getId() == null) {
            throw new EmptyFieldException("id cannot be empty;");
        }
        Organization organization = getValidOrganization(organizationView);
        if (dao.updateOrganization(organization, organizationView.getId()) > 0) {
            return new SuccessResponse("success");
        } else {
            throw new NoObjectException("Обновление не удалось");
        }
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
        return OrganizationView.builder()
                .id(org.getId())
                .name(org.getName())
                .fullName(org.getFullName())
                .inn(org.getInn())
                .kpp(org.getKpp())
                .address(org.getAddress())
                .phone(org.getPhone())
                .isActive(org.getIsActive())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrganizationView> getOrganizationList(OrganizationView organizationView) {
        if (organizationView.getName() == null || organizationView.getName().isEmpty()) {
            throw new EmptyFieldException("Name cannot be empty");
        }
        List<Organization> organizations = dao.getOrganizationList(organizationView.getName(),
                organizationView.getInn(),
                organizationView.getIsActive()
        );

        return (organizations.stream().map(o->OrganizationView.builder()
                                            .id(o.getId())
                                            .name(o.getName())
                                            .isActive(o.getIsActive())
                                            .build())
                                      .collect(Collectors.toList()));


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
        return Organization.builder()
                .name(organizationView.getName())
                .fullName(organizationView.getFullName())
                .inn(organizationView.getInn())
                .kpp(organizationView.getKpp())
                .address(organizationView.getAddress())
                .phone(organizationView.getPhone())
                .isActive(organizationView.getIsActive())
                .build();
    }
}
