package com.am.catalog.service;

import com.am.catalog.view.OrganizationView;

import java.util.List;

/**
 * Сервис класса Organization. Принимает данные из OrganizationController,
 * проверяет и передает в OrganizationDao
 */
public interface OrganizationService {

    /**
     * Добавить организацию
     * @param organizationRq
     * @return
     */
    OrganizationView saveOrganization(OrganizationView organizationRq);

    /**
     * Обновить поля существующей организации
     * @param organizationRq
     * @return
     */
    OrganizationView updateOrganization(OrganizationView organizationRq);

    /**
     * Получить организацию по id
     * @param id
     * @return
     */
    OrganizationView getOrganizationById(Long id);

    /**
     * Получить List организаций, соответствующих параметрам
     * @param name
     * @param inn
     * @param isActive
     * @return
     */
    List<OrganizationView> getOrganizationList(String name, String inn, Boolean isActive);
}
