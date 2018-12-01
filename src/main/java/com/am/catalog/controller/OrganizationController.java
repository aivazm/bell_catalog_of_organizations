package com.am.catalog.controller;

import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.model.Organization;
import com.am.catalog.service.OrganizationService;
import com.am.catalog.view.OrganizationView;
import org.springframework.beans.factory.annotation.Autowired;
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
        return organizationService.idOrg(id);
    }


    @PostMapping("/save")
    public ResponseEntity<String> saveOrg(@RequestBody @Valid OrganizationView organizationView,
                                          BindingResult bindingResult
    ) {
        String message = "Error:\n";
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                message += error.getDefaultMessage() + "\n";
            }
            message = message.trim();
            throw new EmptyFieldException(message);
        } else {
            return organizationService.save(organizationView);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateOrg(@RequestBody @Valid OrganizationView organizationView,
                                            BindingResult bindingResult
    ) {
        String message = "Error:\n";
        if (organizationView.id == null){
            message = "id cannot be empty" + "\n";
        }
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                message += error.getDefaultMessage() + "\n";
            }
            message = message.trim();
        }
        if (!message.equals("Error:\n")){
            throw new EmptyFieldException(message);
        }
        return organizationService.update(organizationView);
    }


    @PostMapping(value = "/list")
    public ResponseEntity<List<Organization>> listOrg(String name,
                                                      @RequestParam(required = false) String inn,
                                                      @RequestParam(required = false) Boolean isActive
    ){
        if(name==null){
            throw new EmptyFieldException("Error:\nName cannot be empty");
        }
        return organizationService.listOrg(name,inn,isActive);
    }

}