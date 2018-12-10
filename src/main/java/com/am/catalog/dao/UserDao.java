package com.am.catalog.dao;

import com.am.catalog.model.User;

import java.util.List;

/**
 * DAO для работы с User
 */
public interface UserDao {

    /**
     * Добавить нового работника
     *
     * @param user User-объект
     */
    void saveUser(User user);

    /**
     * Обновить поля существующего работника
     *
     * @param user User-объект
     */
    void updateUser(User user, Long id);

    /**
     * Получить работника по id
     *
     * @param id id работника
     * @return Объект User;
     */
    User getUserById(Long id);

    /**
     * Получить List работников, соответствующих параметрам
     *
     * @param officeId        id офиса работника
     * @param firstName       имя работника
     * @param secondName      фамилия работника
     * @param middleName      отчество работника
     * @param position        должность работника
     * @param docCode         код документа работника
     * @param citizenshipCode код государства, гражданином которого является работник
     * @return List объектов User;
     */
    List<User> getUserList(Long officeId,
                           String firstName,
                           String secondName,
                           String middleName,
                           String position,
                           String docCode,
                           String citizenshipCode);
}
