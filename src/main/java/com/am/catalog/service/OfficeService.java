package com.am.catalog.service;

import com.am.catalog.dto.OfficeRq;
import com.am.catalog.dto.responses.CrudOperationRs;
import com.am.catalog.dto.responses.office.FindOffByIdRs;
import com.am.catalog.dto.responses.office.GetOffListRs;

public interface OfficeService {

    CrudOperationRs saveOff(OfficeRq officeRq);

    CrudOperationRs updateOff(OfficeRq officeRq);

    FindOffByIdRs findOffById(Long id);

    GetOffListRs getOffList(Long orgId, String name, String phone, Boolean isActive);
}
