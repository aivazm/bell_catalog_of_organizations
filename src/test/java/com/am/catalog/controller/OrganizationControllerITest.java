package com.am.catalog.controller;

import com.am.catalog.model.Organization;
import com.am.catalog.view.ErrorResponse;
import com.am.catalog.view.OrganizationView;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationControllerITest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static String url;

    @Before
    public void createUrl() {
        url = "http://localhost:" + port + "/organization/";
    }


    /**
     * Тест метода OrganizationController#getOrganizationById
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void getOrganizationByIdPositiveTest() {
        Organization organization = new Organization();
        organization.setName("Проверка");
        organization.setFullName("ООО Проверка");
        organization.setInn("0123456789");
        organization.setKpp("012345678");
        organization.setAddress("Адрес");
        organization.setPhone("123456789");
        organization.setActive(true);
        restTemplate.postForLocation(url + "save", organization);

        Wrapper wrapper = restTemplate.getForObject(url + "{id}", Wrapper.class, 3L);
        OrganizationView view = new ObjectMapper().convertValue(wrapper.getData(), OrganizationView.class);
        Assert.assertNotNull(view);
        assertThat(view.getId(), is(3L));
        assertThat(view.getName(), is("Проверка"));
        assertThat(view.getFullName(), is("ООО Проверка"));
        assertThat(view.getInn(), is("0123456789"));
        assertThat(view.getKpp(), is("012345678"));
        assertThat(view.getAddress(), is("Адрес"));
        assertThat(view.getPhone(), is("123456789"));
        assertThat(view.isActive(), is(true));
    }

    /**
     * Тест метода OrganizationController#getOrganizationById с неверными параметрами
     */
    @Test
    public void getOrganizationByIdNoOrganizationTest() {
        Object error = restTemplate.getForObject(
                url + "{id}",
                Object.class,
                3L);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Нет организации с id: 3"));
    }

    /**
     * Тест метода OrganizationController#getOrganizationById с некорректными параметрами
     */
    @Test
    public void getOrganizationByIdInternalServerErrorTest() {
        Object error = restTemplate.getForObject(
                url + "{id}",
                Object.class,
                "w");
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Ошибка на сервере"));
    }

    /**
     * Тест метода OrganizationController#saveOrganization
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void saveOrganizationPositiveTest() {
        OrganizationView organizationView = new OrganizationView();
        organizationView.setName("Рододендрон2");
        organizationView.setFullName("ООО Рододендрон");
        organizationView.setInn("5555555555");
        organizationView.setKpp("012345678");
        organizationView.setAddress("1");

        Wrapper wrapper = restTemplate.postForObject(
                url + "save",
                organizationView,
                Wrapper.class);
        SuccessResponse successResponse = new ObjectMapper().convertValue(wrapper.getData(), SuccessResponse.class);
        Assert.assertNotNull(successResponse);
        assertThat(successResponse.getResult(), is("success"));
    }

    /**
     * Тест метода OrganizationController#saveOrganization с пустыми параметрами
     */
    @Test
    public void saveOrganizationEmptyFieldsTest() {
        OrganizationView organization = new OrganizationView();
        organization.setName("Рододендрон");
        organization.setFullName("ООО Рододендрон");
        organization.setKpp("012345678");
        organization.setAddress("1");

        Object error = restTemplate.postForObject(
                url + "save",
                organization,
                Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("ИНН не может быть пустым;"));
    }

    /**
     * Тест метода OrganizationController#saveOrganization с некорректными параметрами
     */
    @Test
    public void saveOrganizationInternalServerErrorTest() {
        Object error = restTemplate.postForObject(
                url + "save",
                "organization",
                Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Ошибка на сервере"));
    }

    /**
     * Тест метода OrganizationController#updateOrganization
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void updateOrganizationPositiveTest() {
        OrganizationView organizationForSave = new OrganizationView();
        organizationForSave.setName("Проверка");
        organizationForSave.setFullName("ООО Проверка");
        organizationForSave.setInn("0123456789");
        organizationForSave.setKpp("012345678");
        organizationForSave.setAddress("Адрес");
        organizationForSave.setPhone("123456789");
        organizationForSave.setActive(true);
        restTemplate.postForLocation(url + "save", organizationForSave);

        OrganizationView organizationForUpdate = new OrganizationView();
        organizationForUpdate.setId(3L);
        organizationForUpdate.setName("Рододендрон3");
        organizationForUpdate.setFullName("ООО Рододендрон");
        organizationForUpdate.setInn("9876543210");
        organizationForUpdate.setKpp("012345678");
        organizationForUpdate.setAddress("1");
        Wrapper wrapper = restTemplate.postForObject(
                url + "update",
                organizationForUpdate,
                Wrapper.class);

        SuccessResponse successResponse = new ObjectMapper().convertValue(wrapper.getData(), SuccessResponse.class);
        Assert.assertNotNull(successResponse);
        assertThat(successResponse.getResult(), is("success"));
    }

    /**
     * Тест метода OrganizationController#updateOrganization с неверными параметрами
     */
    @Test
    public void updateOrganizationNoUniqueInnTest() {
        OrganizationView organizationForSave = new OrganizationView();
        organizationForSave.setName("Проверка");
        organizationForSave.setFullName("ООО Проверка");
        organizationForSave.setInn("6666666666");
        organizationForSave.setKpp("012345678");
        organizationForSave.setAddress("Адрес");
        organizationForSave.setPhone("123456789");
        organizationForSave.setActive(true);
        restTemplate.postForLocation(url + "save", organizationForSave);

        OrganizationView organizationForUpdate = new OrganizationView();
        organizationForUpdate.setId(3L);
        organizationForUpdate.setName("Родо");
        organizationForUpdate.setFullName("Рододендрон");
        organizationForUpdate.setInn("6666666666");
        organizationForUpdate.setKpp("012345678");
        organizationForUpdate.setAddress("1");
        Object error = restTemplate.postForObject(
                url + "update",
                organizationForUpdate,
                Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Организация с ИНН 6666666666 уже существует"));
    }

    /**
     * Тест метода OrganizationController#updateOrganization с некорректными параметрами
     */
    @Test
    public void updateOrganizationInternalServerErrorTest() {
        Object error = restTemplate.postForObject(
                url + "update",
                "organization",
                Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Ошибка на сервере"));
    }

    /**
     * Тест метода OrganizationController#getListOrganizations
     */
    @Test
    @DirtiesContext(methodMode = AFTER_METHOD)
    public void getListOrganizationsPositiveTest() {
        OrganizationView organizationForSaveFirst = new OrganizationView();
        organizationForSaveFirst.setName("Проверка");
        organizationForSaveFirst.setFullName("ООО Проверка");
        organizationForSaveFirst.setInn("0000000001");
        organizationForSaveFirst.setKpp("012345678");
        organizationForSaveFirst.setAddress("Адрес");
        organizationForSaveFirst.setPhone("123456789");
        organizationForSaveFirst.setActive(true);

        OrganizationView organizationForSaveSecond = new OrganizationView();
        organizationForSaveSecond.setName("Проверка");
        organizationForSaveSecond.setFullName("ООО Проверка");
        organizationForSaveSecond.setInn("0000000002");
        organizationForSaveSecond.setKpp("012345678");
        organizationForSaveSecond.setAddress("Адрес");
        organizationForSaveSecond.setPhone("123456789");
        organizationForSaveSecond.setActive(true);

        restTemplate.postForLocation(url + "save", organizationForSaveFirst);
        restTemplate.postForLocation(url + "save", organizationForSaveSecond);

        OrganizationView organization = new OrganizationView();
        organization.setName("Проверка");
        Wrapper wrapper = restTemplate.postForObject(
                url + "list",
                organization,
                Wrapper.class);

        List viewList = (List) wrapper.getData();
        Assert.assertNotNull(viewList);
        OrganizationView view = new ObjectMapper().convertValue(viewList.get(0), OrganizationView.class);
        assertThat(viewList.size(), is(2));
        assertThat(view.getName(), is("Проверка"));
    }

    /**
     * Тест метода OrganizationController#getListOrganizations с неверными параметрами
     */
    @Test
    public void getListOrganizationsNoObjectTest() {
        OrganizationView organization = new OrganizationView();
        organization.setName("Пион");

        Object error = restTemplate.postForObject(
                url + "list",
                organization,
                Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Организации, удовлетворяющие параметрам, отсутствуют"));
    }

    /**
     * Тест метода OrganizationController#getListOrganizations с пустыми параметрами
     */
    @Test
    public void getListOrganizationsEmptyFieldsTest() {
        OrganizationView organization = new OrganizationView();
        Object error = restTemplate.postForObject(
                url + "list",
                organization,
                Object.class);
        ErrorResponse errorResponse = new ObjectMapper().convertValue(error, ErrorResponse.class);
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Name cannot be empty"));
    }

}
