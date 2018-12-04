package com.am.catalog.service;

import com.am.catalog.dto.OrganizationRequest;
import com.am.catalog.dto.OrganizationResponse;
import com.am.catalog.dto.responses.SuccessResponse;

import java.util.List;


public interface OrganizationService {
    SuccessResponse add(OrganizationRequest organizationRq);

    SuccessResponse updateOrganization(OrganizationRequest organizationRq);

    OrganizationResponse findOrgById(Long id);

    List<OrganizationResponse> getOrgList(String name, String inn, Boolean isActive);
}
