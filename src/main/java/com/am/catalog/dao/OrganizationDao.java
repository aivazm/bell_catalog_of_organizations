package com.am.catalog.dao;

import com.am.catalog.model.Organization;

import java.util.List;

/**
 * DAO для работы с Organization
 */
public interface OrganizationDao {
    /**
     * Добавить новую организацию
     * @param organization
     */
    void saveOrganization(Organization organization);

    /**
     * Обновить поля существующей организации
     * @param organization
     */
    void updateOrganization(Organization organization);

    /**
     * Получить организацию по id
     * @param id
     * @return
     */
    Organization getOrganizationById(Long id);

    /**
     * Получить List организаций, соответствующих параметрам
     * @param name
     * @param inn
     * @param isActive
     * @return
     */
    List<Organization> getOrganizationList(String name, String inn, Boolean isActive);
}