package com.am.catalog.dao;

import com.am.catalog.model.Organization;

import java.util.List;

/**
 * DAO для работы с Organization
 */
public interface OrganizationDao {
    /**
     * Добавить новую организацию
     *
     * @param organization Organization-объект
     */
    void saveOrganization(Organization organization);

    /**
     * Обновить поля существующей организации
     *
     * @param organization Organization-объект
     */
    void updateOrganization(Organization organization, Long id);

    /**
     * Получить организацию по id
     *
     * @param id id организации
     * @return Объект Organization;
     */
    Organization getOrganizationById(Long id);

    /**
     * Получить List организаций, соответствующих параметрам
     *
     * @param name     наименование организации
     * @param inn      ИНН организации
     * @param isActive активность организации
     * @return List объектов Organization;
     */
    List<Organization> getOrganizationList(String name, String inn, Boolean isActive);
}