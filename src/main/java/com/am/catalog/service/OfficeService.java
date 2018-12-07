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
     * @param officeView OfficeView-объект с обязательными параметрами id, address
     * @return Объект OfficeView (result: "success");
     */
    OfficeView saveOffice(OfficeView officeView);

    /**
     * Обновить поля существующего офиса
     * @param officeView OfficeView-объект с обязательными параметрами id, name, address
     * @return Объект OfficeView (result: "success");
     */
    OfficeView updateOffice(OfficeView officeView);

    /**
     * Получить офис по id
     * @param id id офиса
     * @return Объект OfficeView (id, name, address, phone, isActive);
     */
    OfficeView getOfficeById(Long id);

    /**
     * Получить List офисов, соответствующих параметрам
     * @param orgId обязательный параметр id организации
     * @param name  наименование офиса
     * @param phone телефон офиса
     * @param isActive активность офиса
     * @return List объектов OfficeView (id, name, isActive);
     */
    List<OfficeView> getOfficeList(Long orgId, String name, String phone, Boolean isActive);
}
