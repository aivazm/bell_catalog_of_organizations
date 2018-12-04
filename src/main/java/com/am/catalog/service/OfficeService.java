package com.am.catalog.service;

import com.am.catalog.dto.OfficeRequest;
import com.am.catalog.dto.OfficeResponse;

import java.util.List;

public interface OfficeService {

    OfficeResponse addOffice(OfficeRequest officeRequest);

    OfficeResponse updateOffice(OfficeRequest officeRequest);

    OfficeResponse getOfficeById(Long id);

    List<OfficeResponse> getOfficeList(Long orgId, String name, String phone, Boolean isActive);
}
