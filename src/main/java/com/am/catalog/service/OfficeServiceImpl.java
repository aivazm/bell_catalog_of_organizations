package com.am.catalog.service;

import com.am.catalog.dao.OfficeDao;
import com.am.catalog.dao.OrganizationDao;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.model.Office;
import com.am.catalog.model.Organization;
import com.am.catalog.view.OfficeView;
import com.am.catalog.view.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.Servlet;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * {@inheritDoc}
 */
@Service
public class OfficeServiceImpl implements OfficeService {
    private final OfficeDao officeDao;
    private final OrganizationDao organizationDao;
    private final Validator validator;

    @Autowired
    public OfficeServiceImpl(OfficeDao officeDao, OrganizationDao organizationDao, Validator validator) {
        this.officeDao = officeDao;
        this.organizationDao = organizationDao;
        this.validator = validator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SuccessResponse saveOffice(OfficeView officeView) {
        StringBuilder message = new StringBuilder();
        if (officeView.getOrgId() == null) {
            message.append("OrgId cannot be empty; ");
        }

        Set<ConstraintViolation<OfficeView>> validate = validator.validate(officeView);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<OfficeView> violation : validate) {
                message.append(violation.getMessage());
                message.append("; ");
            }
        }
        if (message.length() > 0) {
            throw new EmptyFieldException(message.toString().trim());
        }
        Office office = new Office();
        office.setName(officeView.getName());
        office.setAddress(officeView.getAddress());
        Organization o = organizationDao.getOrganizationById(officeView.getOrgId());
        if (o == null) {
            throw new NoObjectException("Нет организации с id: " + officeView.getOrgId());
        } else {
            office.setOrganization(o);
        }
        if (officeView.getPhone() != null) {
            office.setPhone(officeView.getPhone());
        }
        if (officeView.getIsActive() != null) {
            office.setIsActive(officeView.getIsActive());
        } else {
            office.setIsActive(false);
        }
        officeDao.saveOffice(office);
        return new SuccessResponse("success");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SuccessResponse updateOffice(OfficeView officeView) {
        StringBuilder message = new StringBuilder();
        if (officeView.getId() == null) {
            message.append("id cannot be empty; ");
        }
        Set<ConstraintViolation<OfficeView>> validate = validator.validate(officeView);
        if (!validate.isEmpty()) {
            for (ConstraintViolation<OfficeView> violation : validate) {
                message.append(violation.getMessage());
                message.append("; ");
            }
        }
        if (message.length() > 0) {
            throw new EmptyFieldException(message.toString().trim());
        }
        Office office = new Office();
        office.setName(officeView.getName());
        office.setAddress(officeView.getAddress());
        if (officeView.getOrgId() != null) {
            Organization o = organizationDao.getOrganizationById(officeView.getOrgId());
            if (o == null) {
                throw new NoObjectException("Нет организации с id: " + officeView.getOrgId());
            } else {
                office.setOrganization(o);
            }
        }
        if (officeView.getPhone() != null) {
            office.setPhone(officeView.getPhone());
        }
        if (officeView.getIsActive() != null) {
            office.setIsActive(officeView.getIsActive());
        }
        if (officeDao.updateOffice(office, officeView.getId()) > 0) {
            return new SuccessResponse("success");
        } else {
            throw new NoObjectException("Обновление офиса не удалось");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfficeView getOfficeById(Long id) {
        if (id < 1) {
            throw new EmptyFieldException("Id cannot be empty or less than one");
        }
        Office office = officeDao.getOfficeById(id);
        return OfficeView.builder()
                .id(office.getId())
                .name(office.getName())
                .address(office.getAddress())
                .phone(office.getPhone())
                .isActive(office.getIsActive())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfficeView> getOfficeList(OfficeView officeView) {
        if (officeView.getOrgId() == null || officeView.getOrgId() < 1) {
            throw new EmptyFieldException("OrgId cannot be empty or less than one");
        }
        Organization org = organizationDao.getOrganizationById(officeView.getOrgId());
        if (org != null) {
            List<Office> offices = officeDao.getOfficeList(org,
                    officeView.getName(),
                    officeView.getPhone(),
                    officeView.getIsActive()
            );
            List<OfficeView> viewList = new ArrayList<>();
            for (Office o : offices) {
                viewList.add(OfficeView.builder()
                            .id(o.getId())
                            .name(o.getName())
                            .isActive(o.getIsActive())
                            .build());
            }
            return viewList;
        } else {
            throw new NoObjectException("Нет организации с id: " + officeView.getOrgId());
        }
    }
}
