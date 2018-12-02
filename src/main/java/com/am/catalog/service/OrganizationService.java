package com.am.catalog.service;

import com.am.catalog.dto.CrudOperationRs;
import com.am.catalog.dto.OrganizationRq;
import com.am.catalog.dto.OrganizationRs;
import com.am.catalog.model.Organization;

import java.util.List;


public interface OrganizationService {
    CrudOperationRs saveOrg(OrganizationRq organizationRq);

    CrudOperationRs updateOrg(OrganizationRq organizationRq);

    Organization findOrgById(Long id);

    List<OrganizationRs> getOrgList(String name, String inn, Boolean isActive);
}
