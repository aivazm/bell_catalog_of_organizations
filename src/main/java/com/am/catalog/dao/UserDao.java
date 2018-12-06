package com.am.catalog.dao;

import com.am.catalog.model.User;

import java.util.List;

/**
 * DAO для работы с User
 */
public interface UserDao {

    /**
     * Добавить нового работника
     * @param user
     */
    void saveUser(User user);

    /**
     * Обновить поля существующего работника
     * @param user
     */
    void updateUser(User user);

    /**
     * Получить работника по id
     * @param id
     * @return
     */
    User getUserById(Long id);

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
    List<User> getUserList(Long officeId,
                           String firstName,
                           String secondName,
                           String middleName,
                           String position,
                           String docCode,
                           String citizenshipCode);
}
