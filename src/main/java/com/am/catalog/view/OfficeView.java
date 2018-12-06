package com.am.catalog.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * View-класс Office
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfficeView {
    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Наименование
     */
    @Size(max = 50, message = "Наименование офиса не должно превышать 50 символов")
    @NotEmpty(message = "Наименование офиса не может быть пустым")
    private String name;

    /**
     * Адрес
     */
    @Size(max = 100, message = "Адрес не должен превышать 100 символов")
    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

    /**
     * Телефон
     */
    @Size(max = 20, message = "Номер телефона не должен превышать 20 символов")
    private String phone;

    /**
     * Активность
     */
    private Boolean isActive;

    /**
     * Id организации офиса
     */
    private Long orgId;

    /**
     * Поле для вывода сообщения об успешном выполнении запросов /office/save и /office/update
     */
    private String result;

    public OfficeView() {
    }

    /**
     * Конструктор для вывода данных после успешного выполнения запроса /office/{id}
     * @param id
     * @param name
     * @param address
     * @param phone
     * @param isActive
     */
    public OfficeView(Long id, String name, String address, String phone, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
    }

    /**
     * Конструктор для вывода данных после успешного выполнения запроса /office/list/{orgId}
     * @param id
     * @param name
     * @param isActive
     */
    public OfficeView(Long id, String name, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    /**
     * Конструктор для вывода сообщения об успешном выполнении запросов /office/save и /office/update
     * @param result
     */
    public OfficeView(String result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
