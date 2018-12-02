package com.am.catalog.controller;

import com.am.catalog.dto.CrudOperationRs;
import com.am.catalog.dto.OfficeRq;
import com.am.catalog.dto.OfficeRs;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.service.OfficeService;
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
@RequestMapping(value = "/office", produces = APPLICATION_JSON_VALUE)
public class OfficeController {

    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping("/save")
    public ResponseEntity<CrudOperationRs> saveOff(@RequestBody @Valid OfficeRq officeRq,
                                                   BindingResult bindingResult
    ) {
        StringBuilder message = new StringBuilder();
        if (officeRq.getOrgId() == null) {
            message.append("OrgId cannot be empty | ");
        }
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                message.append(error.getDefaultMessage()).append(" | ");
            }
            throw new EmptyFieldException(message.toString().trim());
        } else {
            CrudOperationRs crudOperationRs = officeService.saveOff(officeRq);
            if (crudOperationRs.getData().isEmpty()) {
                return new ResponseEntity<>(crudOperationRs, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(crudOperationRs, HttpStatus.OK);
            }
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CrudOperationRs> updateOrg(@RequestBody @Valid OfficeRq officeRq,
                                                     BindingResult bindingResult
    ) {
        StringBuilder message = new StringBuilder();
        if (officeRq.getId() == null) {
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
            CrudOperationRs crudOperationRs = officeService.updateOff(officeRq);
            if (crudOperationRs.getData().isEmpty()) {
                return new ResponseEntity<>(crudOperationRs, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(crudOperationRs, HttpStatus.OK);
            }
        }
    }

    @PostMapping(value = "/list/{orgId}")
    public ResponseEntity<List<OfficeRs>> listOff(@PathVariable Long orgId,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String phone,
                                                  @RequestParam(required = false) Boolean isActive
    ) {
        if (orgId == null || orgId < 1) {
            throw new EmptyFieldException("OrgId cannot be empty or less than one");
        }
        List<OfficeRs> listOffRs = officeService.getOffList(orgId, name, phone, isActive);
        return new ResponseEntity<>(listOffRs, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<OfficeRs> idOrg(@PathVariable Long id) {
        if (id < 1) {
            throw new EmptyFieldException("Id cannot be empty or less than one");
        }
        OfficeRs officeRs = officeService.findOffById(id);
        return new ResponseEntity<>(officeRs, HttpStatus.OK);
    }
}