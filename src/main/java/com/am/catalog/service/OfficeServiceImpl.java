package com.am.catalog.service;

import com.am.catalog.dao.OfficeDao;
import com.am.catalog.dto.OfficeRequest;
import com.am.catalog.dto.OfficeResponse;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.model.Office;
import com.am.catalog.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class OfficeServiceImpl implements OfficeService {
    private final OfficeDao dao;
    private final EntityManager em;
    private final Validator validator;

    @Autowired
    public OfficeServiceImpl(OfficeDao dao, EntityManager em, Validator validator) {
        this.dao = dao;
        this.em = em;
        this.validator = validator;
    }

    @Override
    @Transactional
    public OfficeResponse addOffice(OfficeRequest officeRequest) {
        StringBuilder message = new StringBuilder();
        if (officeRequest.getOrgId() == null) {
            message.append("OrgId cannot be empty; ");
        }

        Set<ConstraintViolation<OfficeRequest>> validate = validator.validate(officeRequest);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<OfficeRequest> violation : validate) {
                message.append(violation.getMessage());
                message.append("; ");
            }
        }
        if (message.length() > 0) {
            throw new EmptyFieldException(message.toString().trim());
        }
        Office office = new Office();
        office.setName(officeRequest.getName());
        office.setAddress(officeRequest.getAddress());
        Organization o = em.find(Organization.class, officeRequest.getOrgId());
        if (o == null) {
            throw new NoObjectException("Нет организации с id: " + officeRequest.getOrgId());
        } else {
            office.setOrganization(o);
        }
        if (officeRequest.getPhone() != null) {
            office.setPhone(officeRequest.getPhone());
        }
        if (officeRequest.isActive() != null) {
            office.setActive(officeRequest.isActive());
        } else {
            office.setActive(false);
        }
        dao.saveOffice(office);
        return new OfficeResponse("success");
    }

    @Override
    @Transactional
    public OfficeResponse updateOffice(OfficeRequest officeRequest) {
        StringBuilder message = new StringBuilder();
        if (officeRequest.getId() == null) {
            message.append("id cannot be empty; ");
        }
        Set<ConstraintViolation<OfficeRequest>> validate = validator.validate(officeRequest);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<OfficeRequest> violation : validate) {
                message.append(violation.getMessage());
                message.append("; ");
            }
        }
        if (message.length() > 0) {
            throw new EmptyFieldException(message.toString().trim());
        }
        Office office = new Office();
        office.setId(officeRequest.getId());
        office.setName(officeRequest.getName());
        office.setAddress(officeRequest.getAddress());
        if (officeRequest.getOrgId() != null) {
            Organization o = em.find(Organization.class, officeRequest.getOrgId());
            if (o == null) {
                throw new NoObjectException("Нет организации с id: " + officeRequest.getOrgId());
            } else {
                office.setOrganization(o);
            }
        }
        if (officeRequest.getPhone() != null) {
            office.setPhone(officeRequest.getPhone());
        }
        if (officeRequest.isActive() != null) {
            office.setActive(officeRequest.isActive());
        }
        dao.updateOffice(office);
        return new OfficeResponse("success");
    }

    @Override
    public OfficeResponse getOfficeById(Long id) {
        if (id < 1) {
            throw new EmptyFieldException("Id cannot be empty or less than one");
        }
        Office office = dao.getOfficeById(id);
        return new OfficeResponse(office.getId(),
                office.getName(),
                office.getAddress(),
                office.getPhone(),
                office.isActive());
    }

    @Override
    public List<OfficeResponse> getOfficeList(Long orgId, String name, String phone, Boolean isActive) {
        if (orgId < 1) {
            throw new EmptyFieldException("OrgId cannot be empty or less than one");
        }
        Organization org = em.find(Organization.class, orgId);
        if (org != null) {
            List<Office> offices = dao.getOfficeList(org, name, phone, isActive);
            List<OfficeResponse> officeResponses = new ArrayList<>();
            for (Office o : offices) {
                OfficeResponse officeResponse = new OfficeResponse(o.getId(), o.getName(), o.isActive());
                officeResponses.add(officeResponse);
            }
            return officeResponses;
        } else {
            throw new NoObjectException("Нет организации с id: " + orgId);
        }
    }

}
