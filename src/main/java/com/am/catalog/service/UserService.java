package com.am.catalog.service;

import com.am.catalog.view.UserView;

import java.util.List;

/**
 * Сервис класса User. Принимает данные из UserController,
 * проверяет и передает в UserDao
 */
public interface UserService {
    /**
     * Добавить работника
     * @param userView
     * @return
     */
    UserView saveUser(UserView userView);

    /**
     * Обновить поля существующего работника
     * @param userView
     * @return
     */
    UserView updateUser(UserView userView);

    /**
     * Получить работника по id
     * @param id
     * @return
     */
    UserView getUserById(Long id);

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
    List<UserView> getUserList(Long officeId, String firstName, String secondName, String middleName, String position, String docCode, String citizenshipCode);
}
