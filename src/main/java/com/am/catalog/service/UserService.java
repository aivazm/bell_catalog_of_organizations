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
     * @param userView UserView-объект с обязательными параметрами firstName и position
     * @return Объект UserView (result: "success");
     */
    UserView saveUser(UserView userView);

    /**
     * Обновить поля существующего работника
     * @param userView UserView-объект с обязательными параметрами id, firstName и position
     * @return Объект UserView (result: "success");
     */
    UserView updateUser(UserView userView);

    /**
     * Получить работника по id
     * @param id id работника
     * @return Объект UserView (id, firstName, secondName, middleName, position, phone, docName, docNumber, docDate, citizenshipName, citizenshipCode, isIdentified);
     */
    UserView getUserById(Long id);

    /**
     * Получить List работников, соответствующих параметрам
     * @param officeId обязательный параметр. Id офиса работника
     * @param firstName имя работника
     * @param secondName фамилия работника
     * @param middleName отчество работника
     * @param position должность работника
     * @param docCode код документа работника
     * @param citizenshipCode код государства, гражданином которого является работник
     * @return List объектов UserView (id, firstName, secondName, middleName, position);
     */
    List<UserView> getUserList(Long officeId, String firstName, String secondName, String middleName, String position, String docCode, String citizenshipCode);
}
