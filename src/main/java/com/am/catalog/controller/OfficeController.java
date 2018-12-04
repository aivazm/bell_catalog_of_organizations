package com.am.catalog.controller;

import com.am.catalog.dto.OfficeRequest;
import com.am.catalog.dto.OfficeResponse;
import com.am.catalog.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public OfficeResponse saveOffice(@RequestBody OfficeRequest officeRequest) {
        return officeService.addOffice(officeRequest);
    }

    @PostMapping("/update")
    public OfficeResponse updateOffice(@RequestBody OfficeRequest officeRequest) {
        return officeService.updateOffice(officeRequest);
    }

    @PostMapping(value = "/list/{orgId}")
    public List<OfficeResponse> getListOffices(@PathVariable Long orgId, String name, String phone, Boolean isActive) {
        return officeService.getOfficeList(orgId, name, phone, isActive);
    }

    @GetMapping("{id}")
    public OfficeResponse getOfficeById(@PathVariable Long id) {
        return officeService.getOfficeById(id);
    }
}