package com.am.catalog.controller;

import com.am.catalog.view.ErrorResponse;
import com.am.catalog.view.OfficeView;
import com.am.catalog.view.SuccessResponse;
import com.am.catalog.view.Wrapper;
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
        ParameterizedTypeReference<Wrapper<SuccessResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<Wrapper<SuccessResponse>>() {
                };

        HttpEntity<OfficeView> requestEntity = new HttpEntity<>(office);
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
     * Тест метода OfficeController#saveOffice с пустым обязательным полем
     */
    @Test
    public void saveOfficeEmptyFieldsTest() {
        OfficeView office = new OfficeView();
        office.setAddress("Башня");
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        HttpEntity<OfficeView> requestEntity = new HttpEntity<>(office);
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "save",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
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
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        HttpEntity<OfficeView> requestEntity = new HttpEntity<>(office);
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "save",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
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
        ParameterizedTypeReference<Wrapper<SuccessResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<Wrapper<SuccessResponse>>() {
                };

        HttpEntity<OfficeView> requestEntity = new HttpEntity<>(officeForUpdate);
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
     * Тест метода OfficeController#updateOffice с неверными параметрами
     */
    @Test
    public void updateOfficeNoOrganizationTest() {
        OfficeView office = new OfficeView();
        office.setId(3L);
        office.setName("Слоновая кость");
        office.setAddress("Башня");
        office.setOrgId(3L);
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        HttpEntity<OfficeView> requestEntity = new HttpEntity<>(office);
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "update",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Нет организации с id: 3"));
    }

    /**
     * Тест метода OfficeController#updateOffice с некорректными параметрами
     */
    @Test
    public void updateOfficeInternalServerErrorTest() {
        OfficeView office = new OfficeView();
        office.setName("Нью офис");
        office.setAddress("Столик на фудкорте");
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        HttpEntity<OfficeView> requestEntity = new HttpEntity<>(office);

        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "update",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("id cannot be empty;"));
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
        ParameterizedTypeReference<Wrapper<List<OfficeView>>> parameterizedTypeReference =
                new ParameterizedTypeReference<Wrapper<List<OfficeView>>>() {
                };

        HttpEntity<OfficeView> requestEntity = new HttpEntity<>(office);
        ResponseEntity<Wrapper<List<OfficeView>>> exchange = restTemplate.exchange(
                url + "list",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        Wrapper<List<OfficeView>> wrapper = exchange.getBody();
        assert wrapper != null;
        List<OfficeView> viewList = wrapper.getData();
        Assert.assertNotNull(viewList);
        OfficeView view = viewList.get(2);
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
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        HttpEntity<OfficeView> requestEntity = new HttpEntity<>(office);
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "list",
                HttpMethod.POST,
                requestEntity,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Офисы, удовлетворяющие параметрам, отсутствуют"));
    }

    /**
     * Тест метода OfficeController#getListOffices с некорректными параметрами
     */
    @Test
    public void getListOfficesInternalServerErrorTest() {
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };

        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "list",
                HttpMethod.POST,
                null,
                parameterizedTypeReference
        );
        ErrorResponse errorResponse = exchange.getBody();
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
        officeForSave.setIsActive(true);
        officeForSave.setOrgId(1L);
        restTemplate.postForObject(url + "save", officeForSave, Wrapper.class);

        ParameterizedTypeReference<Wrapper<OfficeView>> parameterizedTypeReference =
                new ParameterizedTypeReference<Wrapper<OfficeView>>() {
                };
        ResponseEntity<Wrapper<OfficeView>> exchange = restTemplate.exchange(
                url + "{id}",
                HttpMethod.GET,
                null,
                parameterizedTypeReference,
                5L
        );
        Wrapper<OfficeView> wrapper = exchange.getBody();
        assert wrapper != null;
        OfficeView view = wrapper.getData();
        Assert.assertNotNull(view);
        assertThat(view.getId(), is(5L));
        assertThat(view.getName(), is("Проверка"));
        assertThat(view.getAddress(), is("Office address"));
        assertThat(view.getPhone(), is("12345678"));
        assertThat(view.getIsActive(), is(true));
    }

    /**
     * Тест метода OfficeController#getOfficeById с неверными параметрами
     */
    @Test
    public void getOfficeByIdNoOfficeTest() {
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "{id}",
                HttpMethod.GET,
                null,
                parameterizedTypeReference,
                5L);
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Нет офиса с id: 5"));
    }

    /**
     * Тест метода OfficeController#getOfficeById с некорректными параметрами
     * Метод ожидает целое число
     * В тесте направляется параметр типа String с нечисловыми символами
     */
    @Test
    public void getOfficeByIdInternalServerErrorTest() {
        ParameterizedTypeReference<ErrorResponse> parameterizedTypeReference =
                new ParameterizedTypeReference<ErrorResponse>() {
                };
        ResponseEntity<ErrorResponse> exchange = restTemplate.exchange(
                url + "{id}",
                HttpMethod.GET,
                null,
                parameterizedTypeReference,
                "wrong parameter");
        ErrorResponse errorResponse = exchange.getBody();
        Assert.assertNotNull(errorResponse);
        assertThat(errorResponse.getError(), is("Ошибка на сервере"));
    }
}
