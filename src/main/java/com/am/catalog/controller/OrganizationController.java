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
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public OrganizationView getOrganizationById(@PathVariable Long id) {
        return organizationService.getOrganizationById(id);
    }

    /**
     * Добавить организацию
     * @param organizationView
     * @return
     */
    @PostMapping("/save")
    public OrganizationView saveOrganization(@RequestBody OrganizationView organizationView) {
        return organizationService.saveOrganization(organizationView);
    }

    /**
     * Обновить поля существующей организации
     * @param organizationView
     * @return
     */
    @PostMapping("/update")
    public OrganizationView updateOrganization(@RequestBody @Valid OrganizationView organizationView) {
        return organizationService.updateOrganization(organizationView);
    }

    /**
     * Получить List организаций, соответствующих параметрам
     * @param name
     * @param inn
     * @param isActive
     * @return
     */
    @PostMapping(value = "/list")
    public List<OrganizationView> getListOrganizations(String name, String inn, Boolean isActive) {
        return organizationService.getOrganizationList(name, inn, isActive);
    }

}