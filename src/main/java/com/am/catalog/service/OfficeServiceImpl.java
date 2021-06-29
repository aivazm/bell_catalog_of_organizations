package com.am.catalog.service;

import com.am.catalog.dao.OfficeDao;
import com.am.catalog.dao.OrganizationDao;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.ErrorMessage;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.model.Office;
import com.am.catalog.model.Organization;
import com.am.catalog.util.ValidationService;
import com.am.catalog.view.OfficeView;
import com.am.catalog.view.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class OfficeServiceImpl implements OfficeService {
    /** сервис для работы с базой данных */
    private final OfficeDao officeDao;
    private final OrganizationDao organizationDao;
    private final ValidationService validationService;

    @Autowired
    public OfficeServiceImpl(OfficeDao officeDao, OrganizationDao organizationDao, ValidationService validationService) {
        this.officeDao = officeDao;
        this.organizationDao = organizationDao;
        this.validationService = validationService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SuccessResponse saveOffice(OfficeView officeView) {
        if (officeView.getOrgId() == null) {
            throw new EmptyFieldException(ErrorMessage.EMPTY_ORG_ID_FIELD);
        }
        Office office = getValidOffice(officeView);
        Organization o = organizationDao.getOrganizationById(officeView.getOrgId());
        if (o == null) {
            throw new NoObjectException(ErrorMessage.NO_ORGANIZATION);
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
        return new SuccessResponse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public SuccessResponse updateOffice(OfficeView officeView) {
        if (officeView.getId() == null) {
            throw new EmptyFieldException(ErrorMessage.EMPTY_ID_FIELD);
        }
        Office office = getValidOffice(officeView);
        if (officeView.getOrgId() != null) {
            Organization o = organizationDao.getOrganizationById(officeView.getOrgId());
            if (o == null) {
                throw new NoObjectException(ErrorMessage.NO_ORGANIZATION);
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
            return new SuccessResponse();
        } else {
            throw new NoObjectException(ErrorMessage.UPDATE_FAILED);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OfficeView getOfficeById(Long id) {
        if (id < 1) {
            throw new EmptyFieldException(String.format(ErrorMessage.INVALID_ID_FIELD, id));
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
        validateOrgId(officeView.getOrgId());
        Organization org = organizationDao.getOrganizationById(officeView.getOrgId());
        if (org != null) {
            List<Office> offices = officeDao.getOfficeList(org,
                    officeView.getName(),
                    officeView.getPhone(),
                    officeView.getIsActive()
            );
            return (offices.stream().map(o -> OfficeView.builder()
                    .id(o.getId())
                    .name(o.getName())
                    .isActive(o.getIsActive())
                    .build()).collect(Collectors.toList()));

        } else {
            throw new NoObjectException(ErrorMessage.NO_ORGANIZATION);
        }
    }

    private Office getValidOffice(OfficeView officeView) {
        validationService.validate(officeView);
        Office office = new Office();
        office.setName(officeView.getName());
        office.setAddress(officeView.getAddress());
        return office;
    }

    private void validateOrgId(Long orgId) {
        if (orgId == null) {
            throw new EmptyFieldException(ErrorMessage.EMPTY_ORG_ID_FIELD);
        }
        if (orgId < 1) {
            throw new EmptyFieldException(String.format(ErrorMessage.INVALID_ORG_ID_FIELD, orgId));
        }
    }
}
