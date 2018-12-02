package com.am.catalog.service;

import com.am.catalog.dao.OrganizationDao;
import com.am.catalog.dto.OrganizationRq;
import com.am.catalog.dto.OrganizationRs;
import com.am.catalog.dto.responses.CrudOperationRs;
import com.am.catalog.dto.responses.organization.FindOrgByIdRs;
import com.am.catalog.dto.responses.organization.GetOrgListRs;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationDao dao;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public CrudOperationRs saveOrg(OrganizationRq OrgRq) {
        Organization organization = new Organization();
        organization.setName(OrgRq.getName());
        organization.setFullName(OrgRq.getFullName());
        organization.setInn(OrgRq.getInn());
        organization.setKpp(OrgRq.getKpp());
        organization.setAddress(OrgRq.getAddress());
        if (OrgRq.getPhone() != null) {
            organization.setPhone(OrgRq.getPhone());
        }
        if (OrgRq.isActive() != null) {
            organization.setActive(OrgRq.isActive());
        } else {
            organization.setActive(false);
        }
        Organization orgSaved = dao.saveOrg(organization);
        CrudOperationRs rs = new CrudOperationRs();
        if (orgSaved != null) {
            rs.setData("Success");
            return rs;
        } else {
            throw new NoObjectException("Организация не создана");
        }
    }

    @Override
    @Transactional
    public CrudOperationRs updateOrg(OrganizationRq OrgRq) {
        Organization organization = new Organization();
        organization.setId(OrgRq.getId());
        organization.setName(OrgRq.getName());
        organization.setFullName(OrgRq.getFullName());
        organization.setInn(OrgRq.getInn());
        organization.setKpp(OrgRq.getKpp());
        organization.setAddress(OrgRq.getAddress());
        if (OrgRq.getPhone() != null) {
            organization.setPhone(OrgRq.getPhone());
        }
        if (OrgRq.isActive() != null) {
            organization.setActive(OrgRq.isActive());
        } else {
            organization.setActive(false);
        }
        Organization orgUpdated = dao.updateOrg(organization);
        CrudOperationRs rs = new CrudOperationRs();
        if (orgUpdated != null) {
            rs.setData("Success");
            return rs;
        } else {
            throw new NoObjectException("Информация не обновлена");
        }
    }

    @Override
    public FindOrgByIdRs findOrgById(Long id) {
        Organization org = dao.findOrgById(id);
        FindOrgByIdRs rs = new FindOrgByIdRs();
        rs.setData(org);
        return rs;
    }

    @Override
    public GetOrgListRs getOrgList(String name, String inn, Boolean isActive) {
        List<OrganizationRs> orgList = dao.getOrgList(name, inn, isActive);
        GetOrgListRs rs = new GetOrgListRs();
        rs.setData(orgList);
        return rs;
    }
}
