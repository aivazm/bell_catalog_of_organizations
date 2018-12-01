package com.am.catalog.dao.organization;

import com.am.catalog.dto.OrganizationRs;
import com.am.catalog.model.Organization;

import java.util.List;

public interface OrganizationDao {

    String saveOrg(Organization organization);

    String updateOrg(Organization organization);

    Organization findOrgById(Long id);

    List<OrganizationRs> getOrgList(String name, String inn, Boolean isActive);
}
