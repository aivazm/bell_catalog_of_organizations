package com.am.catalog.controller;

import com.am.catalog.view.OfficeView;
import com.am.catalog.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Контроллер Office. Принимает запросы и передает в Сервис
 * Возвращает View-объекты
 */
@RestController
@RequestMapping(value = "/office", produces = APPLICATION_JSON_VALUE)
public class OfficeController {
    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    /**
     * Добавить новый офис
     * @param officeView
     * @return
     */
    @PostMapping("/save")
    public OfficeView saveOffice(@RequestBody OfficeView officeView) {
        return officeService.saveOffice(officeView);
    }

    /**
     * Обновить поля существующего офиса
     * @param officeView
     * @return
     */
    @PostMapping("/update")
    public OfficeView updateOffice(@RequestBody OfficeView officeView) {
        return officeService.updateOffice(officeView);
    }

    /**
     * Получить List офисов, соответствующих параметрам
     * @param orgId
     * @param name
     * @param phone
     * @param isActive
     * @return
     */
    @PostMapping(value = "/list/{orgId}")
    public List<OfficeView> getListOffices(@PathVariable Long orgId, String name, String phone, Boolean isActive) {
        return officeService.getOfficeList(orgId, name, phone, isActive);
    }

    /**
     * Получить офис по id
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public OfficeView getOfficeById(@PathVariable Long id) {
        return officeService.getOfficeById(id);
    }
}