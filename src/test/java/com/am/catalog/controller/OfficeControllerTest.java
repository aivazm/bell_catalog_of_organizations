package com.am.catalog.controller;

import com.am.catalog.view.ErrorResponse;
import com.am.catalog.view.OfficeView;
import com.am.catalog.view.SuccessResponse;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfficeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static String url;

    @Before
    public void createUrl() {
        url = "http://localhost:" + port + "/office/";
    }

    /**
     * Тест метода OfficeController#saveOffice
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void saveOfficePositiveTest() {
        OfficeView office = new OfficeView();
        office.setName("Слоновая кость");
        office.setAddress("Башня");
        office.setOrgId(1L);
        Wrapper wrapper = restTemplate.postForObject(url + "save", office, Wrapper.class);
        SuccessResponse successResponse = new ObjectMapper().convertValue(wrapper.getData(), SuccessResponse.class);
        Assert.assertNotNull(successResponse);
        assertThat(successResponse.getResult(), is("success"));
    }

    /**
     * Тест метода OfficeController#saveOffice с пустым обязательным полем
     */
    @Test
    public void saveOfficeEmptyFieldsTest() {
        OfficeView office = new OfficeView();
        office.setAddress("Башня");
        Object error = restTemplate.postForObject(url + "save", office, Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("OrgId cannot be empty; Наименование офиса не может быть пустым;"));
    }

    /**
     * Тест метода OfficeController#saveOffice с дублированием офиса
     */
    @Test
    public void saveOfficeNoUniqueOfficeTest() {
        OfficeView office = new OfficeView();
        office.setName("На Ромашковой");
        office.setAddress("Башня");
        office.setOrgId(1L);
        Object error = restTemplate.postForObject(url + "save", office, Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("В организации с id 1 офис с именем На Ромашковой уже существует"));
    }

    /**
     * Тест метода OfficeController#updateOffice
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void updateOfficePositiveTest() {
        OfficeView officeForSave = new OfficeView();
        officeForSave.setName("Проверка");
        officeForSave.setAddress("Office address");
        officeForSave.setOrgId(1L);
        restTemplate.postForObject(url + "save", officeForSave, Wrapper.class);

        OfficeView officeForUpdate = new OfficeView();
        officeForUpdate.setId(5L);
        officeForUpdate.setName("Слоновая кость");
        officeForUpdate.setAddress("Башня");
        officeForUpdate.setOrgId(2L);
        Wrapper wrapper = restTemplate.postForObject(url + "update", officeForUpdate, Wrapper.class);
        SuccessResponse successResponse = new ObjectMapper().convertValue(wrapper.getData(), SuccessResponse.class);
        Assert.assertNotNull(successResponse);
        assertThat(successResponse.getResult(), is("success"));
    }

    /**
     * Тест метода OfficeController#updateOffice с неверными параметрами
     */
    @Test
    public void updateOfficeNoOrganizationTest() {
        OfficeView office = new OfficeView();
        office.setId(3L);
        office.setName("Слоновая кость");
        office.setAddress("Башня");
        office.setOrgId(3L);
        Object error = restTemplate.postForObject(url + "update", office, Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Нет организации с id: 3"));
    }

    /**
     * Тест метода OfficeController#updateOffice с некорректными параметрами
     */
    @Test
    public void updateOfficeInternalServerErrorTest() {
        Object error = restTemplate.postForObject(url + "update", "office", Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Ошибка на сервере"));
    }

    /**
     * Тест метода OfficeController#getListOffices
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void getListOfficesPositiveTest() {
        OfficeView officeForSaveFirst = new OfficeView();
        officeForSaveFirst.setName("Первый офис");
        officeForSaveFirst.setAddress("Office address");
        officeForSaveFirst.setOrgId(1L);

        OfficeView officeForSaveSecond = new OfficeView();
        officeForSaveSecond.setName("Второй офис");
        officeForSaveSecond.setAddress("Office address");
        officeForSaveSecond.setOrgId(1L);

        restTemplate.postForObject(url + "save", officeForSaveFirst, Wrapper.class);
        restTemplate.postForObject(url + "save", officeForSaveSecond, Wrapper.class);

        OfficeView office = new OfficeView();
        office.setOrgId(1L);
        Wrapper wrapper = restTemplate.postForObject(url + "list", office, Wrapper.class);
        List viewList = (List) wrapper.getData();
        Assert.assertNotNull(viewList);
        OfficeView view = new ObjectMapper().convertValue(viewList.get(2), OfficeView.class);
        assertThat(viewList.size(), is(4));
        assertThat(view.getName(), is("Первый офис"));
    }

    /**
     * Тест метода OfficeController#getListOffices с неверными параметрами
     */
    @Test
    public void getListOfficesNoObjectTest() {
        OfficeView office = new OfficeView();
        office.setOrgId(2L);
        office.setName("На дне");
        Object error = restTemplate.postForObject(url + "list", office, Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Офисы, удовлетворяющие параметрам, отсутствуют"));
    }

    /**
     * Тест метода OfficeController#getListOffices с некорректными параметрами
     */
    @Test
    public void getListOfficesInternalServerErrorTest() {
        Object error = restTemplate.postForObject(url + "list", "office", Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Ошибка на сервере"));
    }

    /**
     * Тест метода OfficeController#getOfficeById
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void getOfficeByIdPositiveTest() {
        OfficeView officeForSave = new OfficeView();
        officeForSave.setName("Проверка");
        officeForSave.setAddress("Office address");
        officeForSave.setPhone("12345678");
        officeForSave.setActive(true);
        officeForSave.setOrgId(1L);
        restTemplate.postForObject(url + "save", officeForSave, Wrapper.class);

        Wrapper wrapper = restTemplate.getForObject(url + "{id}", Wrapper.class, 5L);
        OfficeView view = new ObjectMapper().convertValue(wrapper.getData(), OfficeView.class);
        Assert.assertNotNull(view);
        assertThat(view.getId(), is(5L));
        assertThat(view.getName(), is("Проверка"));
        assertThat(view.getAddress(), is("Office address"));
        assertThat(view.getPhone(), is("12345678"));
        assertThat(view.isActive(), is(true));
    }

    /**
     * Тест метода OfficeController#getOfficeById с неверными параметрами
     */
    @Test
    public void getOfficeByIdNoOfficeTest() {
        Object error = restTemplate.getForObject(url + "{id}", Object.class, 5L);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Нет офиса с id: 5"));
    }

    /**
     * Тест метода OfficeController#getOfficeById с некорректными параметрами
     */
    @Test
    public void getOfficeByIdInternalServerErrorTest() {
        Object error = restTemplate.getForObject(url + "{id}", Object.class, "w");
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Ошибка на сервере"));
    }
}
