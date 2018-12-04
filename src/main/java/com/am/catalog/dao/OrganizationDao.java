package com.am.catalog.dao;

import com.am.catalog.model.Organization;

import java.util.List;

public interface OrganizationDao {

    void saveOrganization(Organization organization);

    void updateOrganization(Organization organization);

    Organization getOrganizationById(Long id);

    List<Organization> getOrganizationList(String name, String inn, Boolean isActive);
}