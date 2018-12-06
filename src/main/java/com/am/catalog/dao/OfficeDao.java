package com.am.catalog.dao;

import com.am.catalog.model.Office;
import com.am.catalog.model.Organization;

import java.util.List;

/**
 * DAO для работы с Office
 */
public interface OfficeDao {

    /**
     * Добавить новый офис
     * @param office
     */
    void saveOffice(Office office);

    /**
     * Обновить поля существующего офиса
     * @param office
     */
    void updateOffice(Office office);

    /**
     * Получить офис по id
     * @param id
     * @return
     */
    Office getOfficeById(Long id);

    /**
     * Получить List офисов, соответствующих параметрам
     * @param org
     * @param name
     * @param phone
     * @param isActive
     * @return
     */
    List<Office> getOfficeList(Organization org, String name, String phone, Boolean isActive);
}
