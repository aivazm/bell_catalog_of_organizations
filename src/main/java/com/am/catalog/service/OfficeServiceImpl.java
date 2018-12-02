package com.am.catalog.service;

import com.am.catalog.dao.OfficeDao;
import com.am.catalog.dto.OfficeRq;
import com.am.catalog.dto.OfficeRs;
import com.am.catalog.dto.responses.CrudOperationRs;
import com.am.catalog.dto.responses.office.FindOffByIdRs;
import com.am.catalog.dto.responses.office.GetOffListRs;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.model.Office;
import com.am.catalog.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {
    private final OfficeDao dao;
    private final EntityManager em;

    @Autowired
    public OfficeServiceImpl(OfficeDao dao, EntityManager em) {
        this.dao = dao;
        this.em = em;
    }

    @Override
    @Transactional
    public CrudOperationRs saveOff(OfficeRq officeRq) {
        Office office = new Office();
        office.setName(officeRq.getName());
        office.setAddress(officeRq.getAddress());
        Organization o = em.find(Organization.class, officeRq.getOrgId());
        if (o == null) {
            throw new NoObjectException("Нет организации с id: " + officeRq.getOrgId());
        } else {
            office.setOrganization(o);
        }
        if (officeRq.getPhone() != null) {
            office.setPhone(officeRq.getPhone());
        }
        if (officeRq.isActive() != null) {
            office.setActive(officeRq.isActive());
        } else {
            office.setActive(false);
        }
        Office officeSaved = dao.saveOff(office);
        CrudOperationRs rs = new CrudOperationRs();
        if (officeSaved != null) {
            rs.setData("Success");
        } else {
            rs.setError("Объект не сохранен");
        }
        return rs;
    }

    @Override
    @Transactional
    public CrudOperationRs updateOff(OfficeRq officeRq) {
        Office office = new Office();
        office.setId(officeRq.getId());
        office.setName(officeRq.getName());
        office.setAddress(officeRq.getAddress());
        if (officeRq.getOrgId() != null) {
            Organization o = em.find(Organization.class, officeRq.getOrgId());
            if (o == null) {
                throw new NoObjectException("Нет организации с id: " + officeRq.getOrgId());
            } else {
                office.setOrganization(o);
            }
        }
        if (officeRq.getPhone() != null) {
            office.setPhone(officeRq.getPhone());
        }
        if (officeRq.isActive() != null) {
            office.setActive(officeRq.isActive());
        } else {
            office.setActive(false);
        }
        Office officeSaved = dao.updateOff(office);
        CrudOperationRs rs = new CrudOperationRs();
        if (officeSaved != null) {
            rs.setData("Success");
        } else {
            rs.setError("Объект не сохранен");
        }
        return rs;
    }

    @Override
    public FindOffByIdRs findOffById(Long id) {
        OfficeRs officeRs = dao.findOffById(id);
        FindOffByIdRs rs = new FindOffByIdRs();
        rs.setData(officeRs);
        return rs;
    }

    @Override
    public GetOffListRs getOffList(Long orgId, String name, String phone, Boolean isActive) {
        Organization org = em.find(Organization.class, orgId);

        if (org != null) {
            GetOffListRs rs = new GetOffListRs();
            List<OfficeRs> offList= dao.getOffList(org, name, phone, isActive);
            rs.setData(offList);
            return rs;
        } else {
            throw new NoObjectException("Нет организации с id: " + orgId);
        }
    }
}
