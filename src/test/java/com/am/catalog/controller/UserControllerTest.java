package com.am.catalog.controller;

import com.am.catalog.view.UserView;
import com.am.catalog.view.Wrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    /**
     * Тест метода UserController#saveUser
     */
    @Test
    public void saveUserPositiveTest(){
        UserView user = new UserView();
        user.setFirstName("Владимир");
        user.setPosition("Селекционер");
        user.setOfficeId(1L);
        Wrapper wrapper = restTemplate.postForObject(
                "http://localhost:" + port + "/user/save",
                user,
                Wrapper.class);
        HashMap view = (HashMap) wrapper.getData();
        assertThat(view.get("result"), is("success"));
    }

    /**
     * Тест метода UserController#saveUser с неверными параметрами
     * @throws Exception
     */
    @Test
    public void saveUserNoOfficeTest() throws Exception {
        UserView user = new UserView();
        user.setFirstName("Владимир");
        user.setPosition("Селекционер");
        user.setOfficeId(10L);
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/user/save",
                user,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Нет офиса с id: 10"));
    }

    /**
     * Тест метода UserController#saveUser с пустыми параметрами
     * @throws Exception
     */
    @Test
    public void saveUserEmptyFieldsTest() throws Exception {
        UserView user = new UserView();
        user.setFirstName("Владимир");
        user.setPosition("Селекционер");

        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/user/save",
                user,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Поле officeId не может быть пустым"));
    }

    /**
     * Тест метода UserController#updateUser
     */
    @Test
    public void updateUserPositiveTest(){
        UserView user = new UserView();
        user.setId(8L);
        user.setFirstName("Владимир");
        user.setPosition("Селекционер");

        Wrapper wrapper = restTemplate.postForObject(
                "http://localhost:" + port + "/user/update",
                user,
                Wrapper.class);
        HashMap view = (HashMap) wrapper.getData();
        assertThat(view.get("result"), is("success"));
    }

    /**
     * Тест метода UserController#updateUser с некорректными параметрами
     * @throws Exception
     */
    @Test
    public void updateUserInternalServerErrorTest() throws Exception {
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/user/update",
                "user",
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Ошибка на сервере"));
    }

    /**
     * Тест метода UserController#updateUser с пустыми параметрами
     * @throws Exception
     */
    @Test
    public void updateUserEmptyFieldsTest() throws Exception {
        UserView user = new UserView();
        user.setId(7L);
        user.setFirstName("Владимир");

        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/user/update",
                user,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Поле position не может быть пустым;"));
    }

    /**
     * Тест метода UserController#getListUsers
     * @throws Exception
     */
    @Test
    public void getListUsersPositiveTest() throws Exception {
        UserView user = new UserView();
        user.setOfficeId(1L);
        user.setFirstName("Роман");
        Wrapper wrapper = restTemplate.postForObject(
                "http://localhost:" + port + "/user/list", user, Wrapper.class);
        List<HashMap> view = (List) wrapper.getData();
        assertThat(view.get(0).get("id"), is(1));
        assertThat(view.get(0).get("firstName"), is("Роман"));
        assertThat(view.get(0).get("secondName"), is("Иванов"));
        assertThat(view.get(0).get("middleName"), is("Петрович"));
        assertThat(view.get(0).get("position"), is("Агроном"));
    }

    /**
     * Тест метода UserController#getListUsers с неверными параметрами
     * @throws Exception
     */
    @Test
    public void getListUsersNoObjectTest() throws Exception {
        UserView user = new UserView();
        user.setOfficeId(1L);
        user.setFirstName("Не Роман");
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/user/list",
                user,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Работники, удовлетворяющие параметрам, отсутствуют"));
    }

    /**
     * Тест метода UserController#getListUsers с некорректными параметрами
     * @throws Exception
     */
    @Test
    public void getListUsersIncorrectOfficeIdTest2() throws Exception {
        UserView user = new UserView();
        user.setOfficeId(-1L);
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/user/list",
                user,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Параметр officeId обязателен к заполнению и не может быть меньше единицы"));
    }

    /**
     * Тест метода UserController#getUserById
     * @throws Exception
     */
    @Test
    public void getUserByIdPositiveTest() throws Exception {
        Wrapper wrapper = restTemplate.getForObject(
                "http://localhost:" + port + "/user/{id}",
                Wrapper.class,
                2L);
        HashMap view = (HashMap) wrapper.getData();
        assertThat(view.get("id"), is(2));
        assertThat(view.get("firstName"), is("Иван"));
        assertThat(view.get("secondName"), is("Петров"));
        assertThat(view.get("middleName"), is("Романович"));
        assertThat(view.get("position"), is("Тракторист"));
        assertThat(view.get("phone"), is("111-01-02"));
        assertThat(view.get("docName"), is("Военный билет"));
        assertThat(view.get("docNumber"), is("1201 612345"));
        assertThat(view.get("docDate"), is("2001-01-14"));
        assertThat(view.get("citizenshipName"), is("Российская Федерация"));
        assertThat(view.get("citizenshipCode"), is("643"));
        assertThat(view.get("identified"), is(true));
    }

    /**
     * Тест метода UserController#getUserById с неверными параметрами
     * @throws Exception
     */
    @Test
    public void getUserByIdNoUserTest() throws Exception {
        Object errorResponse = restTemplate.getForObject(
                "http://localhost:" + port + "/user/{id}",
                Object.class,
                11L);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Нет работника с id: 11"));
    }

    /**
     * Тест метода UserController#getUserById с некорректными параметрами
     * @throws Exception
     */
    @Test
    public void getUserByIdInternalServerErrorTest() throws Exception {
        Object errorResponse = restTemplate.getForObject(
                "http://localhost:" + port + "/user/{id}",
                Object.class,
                "11L");
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Ошибка на сервере"));
    }
}
