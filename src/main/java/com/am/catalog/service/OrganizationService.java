package com.am.catalog.service;

import com.am.catalog.dto.OrganizationRs;
import com.am.catalog.model.Organization;
import com.am.catalog.dto.OrganizationRq;

import java.util.List;


public interface OrganizationService {
    String saveOrg(OrganizationRq organizationRq);

    String updateOrg(OrganizationRq organizationRq);

    Organization findOrgById(Long id);

    List<OrganizationRs> getOrgList(String name, String inn, Boolean isActive);
}
