package com.am.catalog.service;

import com.am.catalog.dao.organization.OrganizationDao;
import com.am.catalog.dto.OrganizationRq;
import com.am.catalog.dto.OrganizationRs;
import com.am.catalog.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
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
    public String saveOrg(OrganizationRq OrgRq) {
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
        String message = dao.saveOrg(organization);
        return message;
    }

    @Override
    @Transactional
    public String updateOrg(OrganizationRq OrgRq) {
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
        String message = dao.updateOrg(organization);
        return message;
    }

    @Override
    public Organization findOrgById(Long id) {
        Organization org = dao.findOrgById(id);
        return org;
    }

    @Override
    public List<OrganizationRs> getOrgList(String name, String inn, Boolean isActive) {
        List<OrganizationRs> orgList = dao.getOrgList(name, inn, isActive);

        return orgList;
    }
}
