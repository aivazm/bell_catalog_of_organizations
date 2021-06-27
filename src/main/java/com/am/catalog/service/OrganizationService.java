package com.am.catalog.service;

import com.am.catalog.view.OrganizationView;
import com.am.catalog.view.SuccessResponse;

import java.util.List;

/**
 * Сервис для обработки запросов работы с организациями.
 * Принимает объекты-отображения OrganizationView, обрабатывает и передает в слой DA.
 */
public interface OrganizationService {

    /**
     * Добавить новую организацию
     *
     * @param organizationView OrganizationView-объект с обязательными параметрами name, fullName, inn, kpp, address
     * @return объект с сообщением о успешном завершении операции
     */
    SuccessResponse saveOrganization(OrganizationView organizationView);

    /**
     * Обновить поля существующей организации
     *
     * @param organizationView объект-представление с обязательными параметрами id, name, fullName, inn, kpp, address
     * @return объект с сообщением о успешном завершении операции
     */
    SuccessResponse updateOrganization(OrganizationView organizationView);

    /**
     * Получить организацию по id
     *
     * @param id id организации
     * @return представление OrganizationView
     */
    OrganizationView getOrganizationById(Long id);

    /**
     * Получить список организаций, отфильтрованных по параметрам
     *
     * @param organizationView OrganizationView-объект с обязательным параметром name
     * @return список представлений OrganizationView (id, name, isActive);
     */

    List<OrganizationView> getOrganizationList(OrganizationView organizationView);
}

