package com.am.catalog.dao.organization;

import com.am.catalog.model.Organization;

import java.util.List;

public interface OrganizationDao {

    String save(Organization organization);

    String update(Organization organization);

    Organization idOrg(Long id);

    List<Organization> listOrg(String name, String inn, Boolean isActive);
}
