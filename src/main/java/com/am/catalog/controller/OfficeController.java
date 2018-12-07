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
     * @param officeView JSON-объект с обязательными параметрами id, address
     * @return Объект OfficeView (result: "success") - результат работы метода officeService.saveOffice(officeView);
     * @see OfficeService#saveOffice(OfficeView)
     */
    @PostMapping("/save")
    public OfficeView saveOffice(@RequestBody OfficeView officeView) {
        return officeService.saveOffice(officeView);
    }

    /**
     * Обновить поля существующего офиса
     * @param officeView JSON-объект с обязательными параметрами id, name, address
     * @return Объект OfficeView (result: "success") - результат работы метода officeService.updateOffice(officeView);
     * @see OfficeService#updateOffice(OfficeView)
     */
    @PostMapping("/update")
    public OfficeView updateOffice(@RequestBody OfficeView officeView) {
        return officeService.updateOffice(officeView);
    }

    /**
     * Получить List офисов, соответствующих параметрам
     * @param orgId обязательный параметр id организации
     * @param name  наименование офиса
     * @param phone телефон офиса
     * @param isActive активность офиса
     * @return List объектов OfficeView (id, name, isActive) - результат работы метода officeService.getOfficeList(orgId, name, phone, isActive);
     * @see OfficeService#getOfficeList(Long orgId, String name, String phone, Boolean isActive)
     */
    @PostMapping(value = "/list/{orgId}")
    public List<OfficeView> getListOffices(@PathVariable Long orgId, String name, String phone, Boolean isActive) {
        return officeService.getOfficeList(orgId, name, phone, isActive);
    }

    /**
     * Получить офис по id
     * @param id PathVariable. id офиса
     * @return Объект OfficeView (id, name, address, phone, isActive) - результат работы метода officeService.getOfficeById(id);

     * @see OfficeService#getOfficeById(Long id)
     */
    @GetMapping("{id}")
    public OfficeView getOfficeById(@PathVariable Long id) {
        return officeService.getOfficeById(id);
    }
}