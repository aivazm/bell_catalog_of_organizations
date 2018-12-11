package com.am.catalog.service;

import com.am.catalog.dao.OfficeDao;
import com.am.catalog.dao.OrganizationDao;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.model.Office;
import com.am.catalog.model.Organization;
import com.am.catalog.view.OfficeView;
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
    public OfficeView saveOffice(OfficeView officeView) {
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
        if (officeView.isActive() != null) {
            office.setActive(officeView.isActive());
        } else {
            office.setActive(false);
        }
        officeDao.saveOffice(office);
        return new OfficeView("success");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OfficeView updateOffice(OfficeView officeView) {
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
        if (officeView.isActive() != null) {
            office.setActive(officeView.isActive());
        }
        officeDao.updateOffice(office, officeView.getId());
        return new OfficeView("success");
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
        return new OfficeView(office.getId(),
                office.getName(),
                office.getAddress(),
                office.getPhone(),
                office.isActive());
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
                    officeView.isActive()
            );
            List<OfficeView> viewList = new ArrayList<>();
            for (Office o : offices) {
                OfficeView view = new OfficeView(o.getId(), o.getName(), o.isActive());
                viewList.add(view);
            }
            return viewList;
        } else {
            throw new NoObjectException("Нет организации с id: " + officeView.getOrgId());
        }
    }
}
