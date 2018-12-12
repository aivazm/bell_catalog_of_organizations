package com.am.catalog.controller;

import com.am.catalog.view.OrganizationView;
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
public class OrganizationControllerITest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    /**
     * Тест метода OrganizationController#getOrganizationById
     * @throws Exception
     */
    @Test
    public void getOrganizationByIdPositiveTest() throws Exception {
        Wrapper wrapper = restTemplate.getForObject(
                "http://localhost:" + port + "/organization/{id}",
                Wrapper.class,
                1L);
        HashMap view = (HashMap) wrapper.getData();
        assertThat(view.get("id"), is(1));
        assertThat(view.get("name"), is("Ромашка"));
    }

    /**
     * Тест метода OrganizationController#getOrganizationById с неверными параметрами
     * @throws NoSuchFieldException
     */
    @Test
    public void getOrganizationByIdNoOrganizationTest() throws NoSuchFieldException {
        Object errorResponse = restTemplate.getForObject(
                "http://localhost:" + port + "/organization/{id}",
                Object.class,
                3L);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Нет организации с id: 3"));
    }

    /**
     * Тест метода OrganizationController#getOrganizationById с некорректными параметрами
     * @throws NoSuchFieldException
     */
    @Test
    public void getOrganizationByIdInternalServerErrorTest() throws NoSuchFieldException {
        Object errorResponse = restTemplate.getForObject(
                "http://localhost:" + port + "/organization/{id}",
                Object.class,
                "w");
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Ошибка на сервере"));
    }

    /**
     * Тест метода OrganizationController#saveOrganization
     * @throws Exception
     */
    @Test
    public void saveOrganizationPositiveTest() throws Exception {
        OrganizationView organization = new OrganizationView();
        organization.setName("Родо");
        organization.setFullName("Рододендрон");
        organization.setInn("5555555555");
        organization.setKpp("012345678");
        organization.setAddress("1");

        Wrapper wrapper = restTemplate.postForObject(
                "http://localhost:" + port + "/organization/save",
                organization,
                Wrapper.class);

        HashMap view = (HashMap) wrapper.getData();
        assertThat(view.get("result"), is("success"));

        Wrapper wrapper2 = restTemplate.getForObject(
                "http://localhost:" + port + "/organization/{id}",
                Wrapper.class,
                3L);
        HashMap view2 = (HashMap) wrapper2.getData();
        assertThat(view2.get("id"), is(3));
        assertThat(view2.get("name"), is("Родо"));
    }

    /**
     * Тест метода OrganizationController#saveOrganization с пустыми параметрами
     * @throws Exception
     */
    @Test
    public void saveOrganizationEmptyFieldsTest() throws Exception {
        OrganizationView organization = new OrganizationView();
        organization.setName("Родо");
        organization.setFullName("Рододендрон");
        organization.setKpp("012345678");
        organization.setAddress("1");

        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/organization/save",
                organization,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("ИНН не может быть пустым;"));
    }

    /**
     * Тест метода OrganizationController#saveOrganization с некорректными параметрами
     * @throws Exception
     */
    @Test
    public void saveOrganizationInternalServerErrorTest() throws Exception {
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/organization/save",
                "organization",
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Ошибка на сервере"));
    }

    /**
     * Тест метода OrganizationController#updateOrganization
     * @throws Exception
     */
    @Test
    public void updateOrganizationPositiveTest() throws Exception {
        OrganizationView organization = new OrganizationView();
        organization.setId(2L);
        organization.setName("Родо");
        organization.setFullName("Рододендрон");
        organization.setInn("0123456789");
        organization.setKpp("012345678");
        organization.setAddress("1");

        Wrapper wrapper = restTemplate.postForObject(
                "http://localhost:" + port + "/organization/update",
                organization,
                Wrapper.class);
        HashMap view = (HashMap) wrapper.getData();
        assertThat(view.get("result"), is("success"));
    }

    /**
     * Тест метода OrganizationController#updateOrganization с неверными параметрами
     * @throws Exception
     */
    @Test
    public void updateOrganizationNoUniqueInnTest() throws Exception {
        OrganizationView organization = new OrganizationView();
        organization.setId(2L);
        organization.setName("Родо");
        organization.setFullName("Рододендрон");
        organization.setInn("1111111111");
        organization.setKpp("012345678");
        organization.setAddress("1");

        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/organization/update",
                organization,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Организация с ИНН 1111111111 уже существует"));
    }

    /**
     * Тест метода OrganizationController#updateOrganization с некорректными параметрами
     * @throws Exception
     */
    @Test
    public void updateOrganizationInternalServerErrorTest() throws Exception {
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/organization/update",
                "organization",
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Ошибка на сервере"));
    }

    /**
     * Тест метода OrganizationController#getListOrganizations
     * @throws Exception
     */
    @Test
    public void getListOrganizationsPositiveTest() throws Exception {
        OrganizationView organization = new OrganizationView();
        organization.setName("Ромашка");
        Wrapper wrapper = restTemplate.postForObject(
                "http://localhost:" + port + "/organization/list",
                organization,
                Wrapper.class);
        List<HashMap> view = (List) wrapper.getData();
        assertThat(view.get(0).get("name"), is("Ромашка"));
    }

    /**
     * Тест метода OrganizationController#getListOrganizations с неверными параметрами
     * @throws Exception
     */
    @Test
    public void getListOrganizationsNoObjectTest() throws Exception {
        OrganizationView organization = new OrganizationView();
        organization.setName("Пион");

        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/organization/list",
                organization,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Организации, удовлетворяющие параметрам, отсутствуют"));
    }

    /**
     * Тест метода OrganizationController#getListOrganizations с пустыми параметрами
     * @throws Exception
     */
    @Test
    public void getListOrganizationsEmptyFieldsTest() throws Exception {
        OrganizationView organization = new OrganizationView();
        Object errorResponse = restTemplate.postForObject(
                "http://localhost:" + port + "/organization/list",
                organization,
                Object.class);
        HashMap errors = (HashMap) errorResponse;
        assertThat(errors.get("error"), is("Name cannot be empty"));
    }

}
