package com.am.catalog.service;

import com.am.catalog.model.Organization;
import com.am.catalog.view.OrganizationView;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface OrganizationService {
    ResponseEntity<String> save(@Valid OrganizationView organizationView);

    ResponseEntity<String> update(@Valid OrganizationView organizationView);

    ResponseEntity<Organization> idOrg(Long id);

    ResponseEntity<List<Organization>> listOrg(String name,
                                               @RequestParam(required = false) String inn,
                                               @RequestParam(required = false) Boolean isActive);
}
