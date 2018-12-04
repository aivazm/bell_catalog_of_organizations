package com.am.catalog.controller;

import com.am.catalog.dto.OrganizationRequest;
import com.am.catalog.dto.OrganizationResponse;
import com.am.catalog.dto.responses.SuccessResponse;
import com.am.catalog.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationController {
    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("{id}")
    public OrganizationResponse getOrganizationById(@PathVariable Long id) {
        return organizationService.findOrgById(id);
    }

    @RequestMapping(value = "/save", method = {POST})
    public SuccessResponse saveOrganization(@RequestBody OrganizationRequest organizationRequest) {
        return organizationService.add(organizationRequest);
    }


    @PostMapping("/update")
    public SuccessResponse updateOrganization(@RequestBody @Valid OrganizationRequest organizationRequest) {
        return organizationService.updateOrganization(organizationRequest);
    }

    @PostMapping(value = "/list")
    public List<OrganizationResponse> getListOrganizations(String name, String inn, Boolean isActive) {
        return organizationService.getOrgList(name, inn, isActive);
    }

}