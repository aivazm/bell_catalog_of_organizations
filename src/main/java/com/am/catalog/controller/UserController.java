package com.am.catalog.controller;

import com.am.catalog.view.UserView;
import com.am.catalog.service.UserService;
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
 * Контроллер User. Принимает запросы и передает в Сервис
 * Возвращает View-объекты
 */
@RestController
@RequestMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * добавить нового работника
     * @param userView
     * @return
     */
    @PostMapping("/save")
    public UserView saveUser(@RequestBody UserView userView) {
        return userService.saveUser(userView);
    }

    /**
     * Обновить поля существующего работника
     * @param userView
     * @return
     */
    @PostMapping("/update")
    public UserView updateUser(@RequestBody UserView userView) {
        return userService.updateUser(userView);
    }

    /**
     * Получить работника по id
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public UserView getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Получить List работников, соответствующих параметрам
     * @param officeId
     * @param firstName
     * @param secondName
     * @param middleName
     * @param position
     * @param docCode
     * @param citizenshipCode
     * @return
     */
    @PostMapping(value = "/list")
    public List<UserView> getListUsers(Long officeId,
                                               String firstName,
                                               String secondName,
                                               String middleName,
                                               String position,
                                               String docCode,
                                               String citizenshipCode
    ) {
        return userService.getUserList(officeId, firstName, secondName, middleName, position, docCode, citizenshipCode);
    }


}
