package com.am.catalog.service;

import com.am.catalog.dao.OrganizationDao;
import com.am.catalog.dto.OrganizationRequest;
import com.am.catalog.dto.OrganizationResponse;
import com.am.catalog.dto.responses.SuccessResponse;
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

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationDao dao;
    private final Validator validator;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao dao, Validator validator) {
        this.dao = dao;
        this.validator = validator;
    }

    @Override
    @Transactional
    public SuccessResponse add(OrganizationRequest organizationRequest) {
        Organization organization = getValidOrganization(organizationRequest);
        dao.saveOrganization(organization);
        return new SuccessResponse("success");
    }

    @Override
    @Transactional
    public SuccessResponse updateOrganization(OrganizationRequest organizationRequest) {
        if (organizationRequest.getId() == null) {
            throw new EmptyFieldException("id cannot be empty;");
        }
        Organization organization = getValidOrganization(organizationRequest);
        organization.setId(organizationRequest.getId());
        dao.updateOrganization(organization);
        return new SuccessResponse("success");
    }

    @Override
    public OrganizationResponse findOrgById(Long id) {
        if (id < 1) {
            throw new EmptyFieldException("Id cannot be empty or less than one");
        }
        Organization org = dao.getOrganizationById(id);
        OrganizationResponse rs = new OrganizationResponse(
                org.getId(),
                org.getName(),
                org.getFullName(),
                org.getInn(),
                org.getKpp(),
                org.getAddress(),
                org.getPhone(),
                org.isActive()
        );
        return rs;
    }

    @Override
    public List<OrganizationResponse> getOrgList(String name, String inn, Boolean isActive) {
        if (name == null || name.equals("")) {
            throw new EmptyFieldException("Name cannot be empty");
        }
        List<Organization> organizations = dao.getOrganizationList(name, inn, isActive);

        List<OrganizationResponse> organizationResponseList = new ArrayList<>();
        for (Organization o : organizations) {
            OrganizationResponse organizationResponse = new OrganizationResponse(o.getId(), o.getName(), o.isActive());
            organizationResponseList.add(organizationResponse);
        }
        return organizationResponseList;
    }

    private Organization getValidOrganization(OrganizationRequest organizationRequest) {
        Set<ConstraintViolation<OrganizationRequest>> validate = validator.validate(organizationRequest);
        if (!validate.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation<OrganizationRequest> violation : validate) {
                message.append(violation.getMessage());
                message.append("; ");
            }
            throw new EmptyFieldException(message.toString().trim());
        }
        Organization organization = new Organization();
        organization.setName(organizationRequest.getName());
        organization.setFullName(organizationRequest.getFullName());
        organization.setInn(organizationRequest.getInn());
        organization.setKpp(organizationRequest.getKpp());
        organization.setAddress(organizationRequest.getAddress());
        if (organizationRequest.getPhone() != null) {
            organization.setPhone(organizationRequest.getPhone());
        }
        if (organizationRequest.isActive() != null) {
            organization.setActive(organizationRequest.isActive());
        } else {
            organization.setActive(false);
        }

        return organization;
    }
}
