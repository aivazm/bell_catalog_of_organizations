package com.am.catalog.controller;

import com.am.catalog.service.OrganizationService;
import com.am.catalog.view.OrganizationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер Organization. Принимает запросы и передает в Сервис
 * Возвращает View-объекты
 */
@RestController
@RequestMapping(value = "/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationController {
    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * ПОлучить организацию по id
     *
     * @param id PathVariable. id организации
     * @return Объект OrganizationView (id, name, fullName, inn, kpp, address, phone, isActive) - результат работы метода organizationService.getOrganizationById(id);
     * @see OrganizationService#getOrganizationById(Long id)
     */
    @GetMapping("{id}")
    public OrganizationView getOrganizationById(@PathVariable Long id) {
        return organizationService.getOrganizationById(id);
    }

    /**
     * Добавить организацию
     *
     * @param organizationView JSON-объект с обязательными параметрами name, fullName, inn, kpp, address
     * @return Объект OrganizationView (result: "success") - результат работы метода organizationService.saveOrganization(organizationView);
     * @see OrganizationService#saveOrganization(OrganizationView organizationRq);
     */
    @PostMapping("/save")
    public OrganizationView saveOrganization(@RequestBody OrganizationView organizationView) {
        return organizationService.saveOrganization(organizationView);
    }

    /**
     * Обновить поля существующей организации
     *
     * @param organizationView JSON-объект с обязательными параметрами id, name, fullName, inn, kpp, address
     * @return Объект OrganizationView (result: "success") - результат работы метода organizationService.updateOrganization(organizationView);
     * @see OrganizationService#updateOrganization(OrganizationView organizationRq);
     */
    @PostMapping("/update")
    public OrganizationView updateOrganization(@RequestBody @Valid OrganizationView organizationView) {
        return organizationService.updateOrganization(organizationView);
    }

    /**
     * Получить List организаций, соответствующих параметрам
     *
     * @param organizationView JSON-объект с обязательным параметром name
     * @return List объектов OrganizationView (id, name, isActive) - результат работы метода organizationService.getOrganizationList(organizationView);
     */
    @PostMapping("/list")
    public List<OrganizationView> getListOrganizations(@RequestBody OrganizationView organizationView) {
        return organizationService.getOrganizationList(organizationView);
    }
}