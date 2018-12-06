package com.am.catalog.service;

import com.am.catalog.view.OfficeView;

import java.util.List;

/**
 * Сервис класса Office. Принимает данные из OfficeController,
 * проверяет и передает в OfficeDao
 */
public interface OfficeService {

    /**
     * Добавить офис
     * @param officeView
     * @return
     */
    OfficeView saveOffice(OfficeView officeView);

    /**
     * Обновить поля существующего офиса
     * @param officeView
     * @return
     */
    OfficeView updateOffice(OfficeView officeView);

    /**
     * Получить офис по id
     * @param id
     * @return
     */
    OfficeView getOfficeById(Long id);

    /**
     * Получить List офисов, соответствующих параметрам
     * @param orgId
     * @param name
     * @param phone
     * @param isActive
     * @return
     */
    List<OfficeView> getOfficeList(Long orgId, String name, String phone, Boolean isActive);
}
