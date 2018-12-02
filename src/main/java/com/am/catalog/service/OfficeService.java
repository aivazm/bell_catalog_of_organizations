package com.am.catalog.service;

import com.am.catalog.dto.CrudOperationRs;
import com.am.catalog.dto.OfficeRq;
import com.am.catalog.dto.OfficeRs;

import java.util.List;

public interface OfficeService {

    CrudOperationRs saveOff(OfficeRq officeRq);

    CrudOperationRs updateOff(OfficeRq officeRq);

    OfficeRs findOffById(Long id);

    List<OfficeRs> getOffList(Long orgId, String name, String phone, Boolean isActive);
}
