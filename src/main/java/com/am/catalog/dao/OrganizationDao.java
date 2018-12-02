package com.am.catalog.dao;

import com.am.catalog.dto.OrganizationRs;
import com.am.catalog.model.Organization;

import java.util.List;

public interface OrganizationDao {

    Organization saveOrg(Organization organization);

    Organization updateOrg(Organization organization);

    Organization findOrgById(Long id);

    List<OrganizationRs> getOrgList(String name, String inn, Boolean isActive);
}