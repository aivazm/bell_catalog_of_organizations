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
     * @param office Office-объект
     */
    void saveOffice(Office office);

    /**
     * Обновить поля существующего офиса
     * @param office Office-объект
     */
    void updateOffice(Office office);

    /**
     * Получить офис по id
     * @param id id офиса
     * @return Объект Office;
     */
    Office getOfficeById(Long id);

    /**
     * Получить List офисов, соответствующих параметрам
     * @param org Organization-объект
     * @param name наименование офиса
     * @param phone телефон офиса
     * @param isActive активность офиса
     * @return List объектов Office;
     */
    List<Office> getOfficeList(Organization org, String name, String phone, Boolean isActive);
}
