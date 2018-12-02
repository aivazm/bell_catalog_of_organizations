package com.am.catalog.service;

import com.am.catalog.dto.responses.CrudOperationRs;
import com.am.catalog.dto.responses.organization.FindOrgByIdRs;
import com.am.catalog.dto.responses.organization.GetOrgListRs;
import com.am.catalog.dto.OrganizationRq;


public interface OrganizationService {
    CrudOperationRs saveOrg(OrganizationRq organizationRq);

    CrudOperationRs updateOrg(OrganizationRq organizationRq);

    FindOrgByIdRs findOrgById(Long id);

    GetOrgListRs getOrgList(String name, String inn, Boolean isActive);
}
