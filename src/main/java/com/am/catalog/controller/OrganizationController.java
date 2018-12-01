package com.am.catalog.controller;

import com.am.catalog.dto.CrudOperationRs;
import com.am.catalog.dto.OrganizationRq;
import com.am.catalog.dto.OrganizationRs;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.model.Organization;
import com.am.catalog.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Organization> idOrg(@PathVariable Long id) {
        Organization organization = organizationService.findOrgById(id);
        return new ResponseEntity<>(organization, HttpStatus.OK);
    }


    @PostMapping("/save")
    public ResponseEntity<CrudOperationRs> saveOrg(@RequestBody @Valid OrganizationRq organizationRq,
                                                   BindingResult bindingResult
    ) {
        StringBuilder message = new StringBuilder();
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                message.append(error.getDefaultMessage()).append(" | ");
            }
            throw new EmptyFieldException(message.toString().trim());
        } else {
            CrudOperationRs crudOperationRs = new CrudOperationRs();
            crudOperationRs.setData(organizationService.saveOrg(organizationRq));
            return new ResponseEntity<>(crudOperationRs, HttpStatus.OK);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CrudOperationRs> updateOrg(@RequestBody @Valid OrganizationRq organizationRq,
                                                     BindingResult bindingResult
    ) {
        StringBuilder message = new StringBuilder();
        if (organizationRq.getId() == null) {
            message.append("id cannot be empty | ");
        }
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                message.append(error.getDefaultMessage()).append(" | ");
            }
        }
        if (message.length() != 0) {
            throw new EmptyFieldException(message.toString());
        } else {
            CrudOperationRs crudOperationRs = new CrudOperationRs();
            crudOperationRs.setData(organizationService.updateOrg(organizationRq));
            return new ResponseEntity<>(crudOperationRs, HttpStatus.OK);
        }
    }


    @PostMapping(value = "/list")
    public ResponseEntity<List<OrganizationRs>> listOrg(String name,
                                                        @RequestParam(required = false) String inn,
                                                        @RequestParam(required = false) Boolean isActive
    ) {
        if (name == null || name.equals("")) {
            throw new EmptyFieldException("Name cannot be empty");
        }
        List<OrganizationRs> listOrgRs = organizationService.getOrgList(name, inn, isActive);
        return new ResponseEntity<>(listOrgRs, HttpStatus.OK);
    }

}