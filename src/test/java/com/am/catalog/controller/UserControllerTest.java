package com.am.catalog.controller;

import com.am.catalog.view.ErrorResponse;
import com.am.catalog.view.SuccessResponse;
import com.am.catalog.view.UserView;
import com.am.catalog.view.Wrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static String url;

    @Before
    public void createUrl() {
        url = "http://localhost:" + port + "/user/";
    }

    /**
     * Тест метода UserController#saveUser
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void saveUserPositiveTest() {
        UserView user = new UserView();
        user.setFirstName("Владимир");
        user.setSecondName("Ерофеев");
        user.setMiddleName("Александрович");
        user.setPosition("Селекционер");
        user.setPhone("551-551");
        user.setDocCode("21");
        user.setDocName("Паспорт");
        user.setDocNumber("1234 123459");
        user.setDocDate(new Date(2010 - 02 - 20));
        user.setCitizenshipCode("643");
        user.setIdentified(true);
        user.setOfficeId(1L);

        ParameterizedTypeReference<Wrapper<SuccessResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<Wrapper<SuccessResponse>>() {
                };

        HttpEntity<UserView> requestEntity = new HttpEntity<>(user);
        ResponseEntity<Wrapper<SuccessResponse>> exchange = restTemplate.exchange(
                url + "save",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        Wrapper<SuccessResponse> wrapper = exchange.getBody();
        assert wrapper != null;
        SuccessResponse successResponse = wrapper.getData();
        Assert.assertNotNull(successResponse);
        assertThat(successResponse.getResult(), is("success"));
    }

    /**
     * Тест метода UserController#saveUser с неверными параметрами
     */
    @Test
    public void saveUserNoOfficeTest() {
        UserView user = new UserView();
        user.setFirstName("Владимир");
        user.setPosition("Селекционер");
        user.setOfficeId(10L);
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        HttpEntity<UserView> requestEntity = new HttpEntity<>(user);
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "save",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Нет офиса с id: 10"));
    }

    /**
     * Тест метода UserController#saveUser с пустыми параметрами
     */
    @Test
    public void saveUserEmptyFieldsTest() {
        UserView user = new UserView();
        user.setFirstName("Владимир");
        user.setPosition("Селекционер");
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        HttpEntity<UserView> requestEntity = new HttpEntity<>(user);
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "save",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Поле officeId не может быть пустым"));
    }

    /**
     * Тест метода UserController#updateUser
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void updateUserPositiveTest() {
        UserView userForSave = new UserView();
        userForSave.setFirstName("Владимир");
        userForSave.setSecondName("Ерофеев");
        userForSave.setMiddleName("Александрович");
        userForSave.setPosition("Селекционер");
        userForSave.setPhone("551-551");
        userForSave.setDocCode("21");
        userForSave.setDocName("Паспорт");
        userForSave.setDocNumber("1234 123459");
        userForSave.setDocDate(new Date(2010 - 02 - 20));
        userForSave.setCitizenshipCode("643");
        userForSave.setIdentified(true);
        userForSave.setOfficeId(1L);
        restTemplate.postForObject(url + "save", userForSave, Wrapper.class);

        UserView user = new UserView();
        user.setId(9L);
        user.setFirstName("Илья");
        user.setSecondName("Кузнецов");
        user.setMiddleName("Кузьмич");
        user.setPosition("Рабочий");
        user.setPhone("222-333");
        user.setDocCode("21");
        user.setDocName("Паспорт");
        user.setDocNumber("1222 123459");
        user.setDocDate(new Date(2010 - 02 - 10));
        user.setCitizenshipCode("643");
        user.setIdentified(true);
        user.setOfficeId(2L);
        ParameterizedTypeReference<Wrapper<SuccessResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<Wrapper<SuccessResponse>>() {
                };

        HttpEntity<UserView> requestEntity = new HttpEntity<>(user);
        ResponseEntity<Wrapper<SuccessResponse>> exchange = restTemplate.exchange(
                url + "update",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        Wrapper<SuccessResponse> wrapper = exchange.getBody();
        assert wrapper != null;
        SuccessResponse successResponse = wrapper.getData();
        Assert.assertNotNull(successResponse);
        assertThat(successResponse.getResult(), is("success"));
    }

    /**
     * Тест метода UserController#updateUser с некорректными параметрами
     */
    @Test
    public void updateUserInternalUnknownDocumentTest() {
        UserView user = new UserView();
        user.setId(8L);
        user.setFirstName("Илья");
        user.setPosition("Рабочий");
        user.setDocName("Справка");
        user.setDocNumber("1222 123459");
        user.setDocDate(new Date(2010 - 02 - 10));

        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };
        HttpEntity<UserView> requestEntity = new HttpEntity<>(user);
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "update",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("По указанным параметрам тип документа не установлен;"));
    }

    /**
     * Тест метода UserController#updateUser с пустыми параметрами
     */
    @Test
    public void updateUserEmptyFieldsTest() {
        UserView user = new UserView();
        user.setId(7L);
        user.setFirstName("Владимир");
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };
        HttpEntity<UserView> requestEntity = new HttpEntity<>(user);
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "update",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Поле position не может быть пустым;"));
    }

    /**
     * Тест метода UserController#getListUsers
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void getListUsersPositiveTest() {
        UserView userForSave = new UserView();
        userForSave.setFirstName("Владимир");
        userForSave.setSecondName("Ерофеев");
        userForSave.setMiddleName("Александрович");
        userForSave.setPosition("Селекционер");
        userForSave.setPhone("551-551");
        userForSave.setDocCode("07");
        userForSave.setDocName("Военный билет");
        userForSave.setDocNumber("1234 123459");
        userForSave.setDocDate(new Date(2010 - 02 - 20));
        userForSave.setCitizenshipCode("104");
        userForSave.setIdentified(true);
        userForSave.setOfficeId(2L);
        restTemplate.postForObject(url + "save", userForSave, Wrapper.class);

        UserView user = new UserView();
        user.setOfficeId(2L);
        user.setFirstName("Владимир");
        user.setSecondName("Ерофеев");
        user.setMiddleName("Александрович");
        user.setPosition("Селекционер");
        user.setDocCode("07");
        user.setCitizenshipCode("104");

        ParameterizedTypeReference<Wrapper<List<UserView>>> parameterizedTypeReference =
                new ParameterizedTypeReference<Wrapper<List<UserView>>>() {
                };

        HttpEntity<UserView> requestEntity = new HttpEntity<>(user);
        ResponseEntity<Wrapper<List<UserView>>> exchange = restTemplate.exchange(
                url + "list",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        Wrapper<List<UserView>> wrapper = exchange.getBody();
        assert wrapper != null;
        List<UserView> viewList = wrapper.getData();
        Assert.assertNotNull(viewList);
        UserView view = viewList.get(0);
        assertThat(viewList.size(), is(1));
        assertThat(view.getId(), is(9L));
        assertThat(view.getFirstName(), is("Владимир"));
        assertThat(view.getSecondName(), is("Ерофеев"));
        assertThat(view.getMiddleName(), is("Александрович"));
        assertThat(view.getPosition(), is("Селекционер"));
    }

    /**
     * Тест метода UserController#getListUsers с неверными параметрами
     */
    @Test
    public void getListUsersNoObjectTest() {
        UserView user = new UserView();
        user.setOfficeId(1L);
        user.setFirstName("Не Роман");
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        HttpEntity<UserView> requestEntity = new HttpEntity<>(user);
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "list",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Работники, удовлетворяющие параметрам, отсутствуют"));
    }

    /**
     * Тест метода UserController#getListUsers с некорректными параметрами
     */
    @Test
    public void getListUsersIncorrectOfficeIdTest2() {
        UserView user = new UserView();
        user.setOfficeId(-1L);
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        HttpEntity<UserView> requestEntity = new HttpEntity<>(user);
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "list",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Параметр officeId обязателен к заполнению и не может быть меньше единицы"));
    }

    /**
     * Тест метода UserController#getUserById
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void getUserByIdPositiveTest() {
        UserView userForSave = new UserView();
        userForSave.setFirstName("Владимир");
        userForSave.setSecondName("Ерофеев");
        userForSave.setMiddleName("Александрович");
        userForSave.setPosition("Селекционер");
        userForSave.setPhone("551-551");
        userForSave.setDocCode("07");
        userForSave.setDocName("Военный билет");
        userForSave.setDocNumber("1234 123459");
        userForSave.setDocDate(new Date());
        userForSave.setCitizenshipCode("643");
        userForSave.setIdentified(true);
        userForSave.setOfficeId(2L);
        restTemplate.postForObject(url + "save", userForSave, Wrapper.class);

        ParameterizedTypeReference<Wrapper<UserView>> parameterizedTypeReference =
                new ParameterizedTypeReference<Wrapper<UserView>>() {
                };
        ResponseEntity<Wrapper<UserView>> exchange = restTemplate.exchange(
                url + "{id}",
                HttpMethod.GET,
                null,
                parameterizedTypeReference,
                9L
        );
        Wrapper<UserView> wrapper = exchange.getBody();
        assert wrapper != null;
        UserView view = wrapper.getData();
        assertThat(view.getId(), is(9L));
        assertThat(view.getFirstName(), is("Владимир"));
        assertThat(view.getSecondName(), is("Ерофеев"));
        assertThat(view.getMiddleName(), is("Александрович"));
        assertThat(view.getPosition(), is("Селекционер"));
        assertThat(view.getPhone(), is("551-551"));
        assertThat(view.getDocName(), is("Военный билет"));
        assertThat(view.getDocNumber(), is("1234 123459"));
        assertThat(view.getCitizenshipName(), is("Российская Федерация"));
        assertThat(view.getCitizenshipCode(), is("643"));
        assertThat(view.isIdentified(), is(true));
    }

    /**
     * Тест метода UserController#getUserById с неверными параметрами
     */
    @Test
    public void getUserByIdNoUserTest() {
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "{id}",
                HttpMethod.GET,
                null,
                parameterizedTypeReference,
                9L
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Нет работника с id: 9"));
    }

    /**
     * Тест метода UserController#getUserById с некорректными параметрами
     */
    @Test
    public void getUserByIdInternalServerErrorTest() {
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "{id}",
                HttpMethod.GET,
                null,
                parameterizedTypeReference,
                "not number"
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Ошибка на сервере"));
    }
}
