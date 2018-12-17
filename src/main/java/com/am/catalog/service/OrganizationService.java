package com.am.catalog.service;

import com.am.catalog.view.OrganizationView;
import com.am.catalog.view.SuccessResponse;

import java.util.List;

/**
 * Сервис класса Organization. Принимает данные из OrganizationController,
 * проверяет и передает в OrganizationDao
 */
public interface OrganizationService {

    /**
     * Добавить организацию
     *
     * @param organizationView OrganizationView-объект с обязательными параметрами name, fullName, inn, kpp, address
     * @return Объект OrganizationView (result: "success");
     */
    SuccessResponse saveOrganization(OrganizationView organizationView);

    /**
     * Обновить поля существующей организации
     *
     * @param organizationView OrganizationView-объект с обязательными параметрами id, name, fullName, inn, kpp, address
     * @return Объект OrganizationView (result: "success");
     */
    SuccessResponse updateOrganization(OrganizationView organizationView);

    /**
     * Получить организацию по id
     *
     * @param id id организации
     * @return Объект OrganizationView (id, name, fullName, inn, kpp, address, phone, isActive);
     */
    OrganizationView getOrganizationById(Long id);

    /**
     * Получить List организаций, соответствующих параметрам
     *
     * @param organizationView OrganizationView-объект с обязательным параметром name
     * @return List объектов OrganizationView (id, name, isActive);
     */

    List<OrganizationView> getOrganizationList(OrganizationView organizationView);
}

