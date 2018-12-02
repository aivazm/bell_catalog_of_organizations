package com.am.catalog.dao;

import com.am.catalog.dto.OfficeRs;
import com.am.catalog.model.Office;
import com.am.catalog.model.Organization;

import java.util.List;

public interface OfficeDao {

    Office saveOff(Office office);

    Office updateOff(Office office);

    OfficeRs findOffById(Long id);

    List<OfficeRs> getOffList(Organization org, String name, String phone, Boolean isActive);
}
