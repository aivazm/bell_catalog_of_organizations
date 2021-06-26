package com.am.catalog.controller;

import com.am.catalog.service.OrganizationService;
import com.am.catalog.view.OrganizationView;
import com.am.catalog.view.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/** Контроллер запросов для работы с организациями */
@RestController
@RequestMapping(value = "/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationController {

    /** Сервис для работы с организациями */
    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * Получить организацию по id
     *
     * @param id идентификатор организации
     * @return объект-представление организации
     * @see OrganizationService#getOrganizationById(Long id)
     */
    @GetMapping("{id}")
    public OrganizationView getOrganizationById(@PathVariable Long id) {
        return organizationService.getOrganizationById(id);
    }

    /**
     * Добавить новую организацию
     *
     * @param organizationView объект-представление организации с обязательными параметрами name, fullName, inn, kpp, address
     * @return сообщение об успешном результате операции
     * @see OrganizationService#saveOrganization(OrganizationView organizationRq);
     */
    @PostMapping("/save")
    public SuccessResponse saveOrganization(@RequestBody OrganizationView organizationView) {
        return organizationService.saveOrganization(organizationView);
    }

    /**
     * Обновить поля существующей организации
     *
     * @param organizationView объект-представление организации с обязательными параметрами id, name, fullName, inn, kpp, address
     * @return сообщение об успешном результате операции
     * @see OrganizationService#updateOrganization(OrganizationView organizationRq);
     */
    @PostMapping("/update")
    public SuccessResponse updateOrganization(@RequestBody OrganizationView organizationView) {
        return organizationService.updateOrganization(organizationView);
    }

    /**
     * Получить список организаций, отфильтрованный по параметрам
     *
     * @param organizationView объект-представление организации с обязательным параметром name
     * @return список объектов-представлений организации
     */
    @PostMapping("/list")
    public List<OrganizationView> getListOrganizations(@RequestBody OrganizationView organizationView) {
        return organizationService.getOrganizationList(organizationView);
    }
}