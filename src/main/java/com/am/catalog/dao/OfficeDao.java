package com.am.catalog.dao;

import com.am.catalog.model.Office;
import com.am.catalog.model.Organization;

import java.util.List;

public interface OfficeDao {

    void saveOffice(Office office);

    void updateOffice(Office office);

    Office getOfficeById(Long id);

    List<Office> getOfficeList(Organization org, String name, String phone, Boolean isActive);
}
