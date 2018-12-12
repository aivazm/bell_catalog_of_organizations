package com.am.catalog.controller;

import com.am.catalog.view.OfficeView;
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
public class OfficeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    /**
     * Тест метода OfficeController#saveOffice
     * @throws Exception
     */
    @Test
    public void saveOfficePositiveTest() throws Exception {
        OfficeView office = new OfficeView();
        office.setName("Слоновая кость");
        office.setAddress("Башня");
        office.setOrgId(1L);
        Wrapper wrapper = restTemplate.postForObject(
                "http://localhost:" + port + "/office/save",
                office,
                Wrapper.class);
        HashMap view = (HashMap) wrapper.getData();
        assertThat(view.get("result"), is("success"));
    }

    /**
     * Тест метода OfficeController#saveOffice с пустым обязательным полем
     * @throws Exception
     */
    @Test
    public void saveOfficeEmptyFieldsTest() throws Exception {
        OfficeView office = new OfficeView();
        office.setAddress("Башня");

        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/office/save",
                office,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("OrgId cannot be empty; Наименование офиса не может быть пустым;"));
    }

    /**
     * Тест метода OfficeController#saveOffice с дублированием офиса
     * @throws Exception
     */
    @Test
    public void saveOfficeNoUniqueOfficeTest() throws Exception {
        OfficeView office = new OfficeView();
        office.setName("На Ромашковой");
        office.setAddress("Башня");
        office.setOrgId(1L);
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/office/save",
                office,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("В организации с id 1 офис с именем На Ромашковой уже существует"));
    }

    /**
     * Тест метода OfficeController#updateOffice
     * @throws Exception
     */
    @Test
    public void updateOfficePositiveTest() throws Exception {
        OfficeView office = new OfficeView();
        office.setId(3L);
        office.setName("Слоновая кость");
        office.setAddress("Башня");
        office.setOrgId(2L);
        Wrapper wrapper = restTemplate.postForObject(
                "http://localhost:" + port + "/office/update",
                office,
                Wrapper.class);
        HashMap view = (HashMap) wrapper.getData();
        assertThat(view.get("result"), is("success"));
    }

    /**
     * Тест метода OfficeController#updateOffice с неверными параметрами
     * @throws Exception
     */
    @Test
    public void updateOfficeNoOrganizationTest() throws Exception {
        OfficeView office = new OfficeView();
        office.setId(3L);
        office.setName("Слоновая кость");
        office.setAddress("Башня");
        office.setOrgId(3L);
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/office/update", office, Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Нет организации с id: 3"));
    }

    /**
     * Тест метода OfficeController#updateOffice с некорректными параметрами
     * @throws Exception
     */
    @Test
    public void updateOfficeInternalServerErrorTest() throws Exception {
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/office/update", "office", Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Ошибка на сервере"));
    }

    /**
     * Тест метода OfficeController#getListOffices
     * @throws Exception
     */
    @Test
    public void getListOfficesPositiveTest() throws Exception {
        OfficeView office = new OfficeView();
        office.setOrgId(1L);
        office.setName("На Ромашковой");
        Wrapper wrapper = restTemplate.postForObject(
                "http://localhost:" + port + "/office/list", office, Wrapper.class);
        List<HashMap> view = (List) wrapper.getData();
        assertThat(view.get(0).get("name"), is("На Ромашковой"));
    }

    /**
     * Тест метода OfficeController#getListOffices с неверными параметрами
     * @throws Exception
     */
    @Test
    public void getListOfficesNoObjectTest() throws Exception {
        OfficeView office = new OfficeView();
        office.setOrgId(2L);
        office.setName("На дне");
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/office/list",
                office,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Офисы, удовлетворяющие параметрам, отсутствуют"));
    }

    /**
     * Тест метода OfficeController#getListOffices с некорректными параметрами
     * @throws Exception
     */
    @Test
    public void getListOfficesInternalServerErrorTest() throws Exception {

        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/office/list",
                "office",
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Ошибка на сервере"));
    }

    /**
     * Тест метода OfficeController#getOfficeById
     * @throws Exception
     */
    @Test
    public void getOfficeByIdPositiveTest() throws Exception {
        Wrapper wrapper = restTemplate.getForObject(
                "http://localhost:" + port + "/office/{id}",
                Wrapper.class,
                1L);
        HashMap view = (HashMap) wrapper.getData();
        assertThat(view.get("id"), is(1));
        assertThat(view.get("name"), is("На Ромашковой"));
        assertThat(view.get("address"), is("Ромашковая, 1"));
        assertThat(view.get("phone"), is("111-11-01"));
        assertThat(view.get("active"), is(true));
    }

    /**
     * Тест метода OfficeController#getOfficeById с неверными параметрами
     * @throws Exception
     */
    @Test
    public void getOfficeByIdNoOfficeTest() throws Exception {
        Object errorResponse = restTemplate.getForObject(
                "http://localhost:" + port + "/office/{id}",
                Object.class,
                8L);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Нет офиса с id: 8"));
    }

    /**
     * Тест метода OfficeController#getOfficeById с некорректными параметрами
     * @throws Exception
     */
    @Test
    public void getOfficeByIdInternalServerErrorTest() throws Exception {
        Object errorResponse = restTemplate.getForObject(
                "http://localhost:" + port + "/office/{id}",
                Object.class,
                "w");
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Ошибка на сервере"));
    }
}
