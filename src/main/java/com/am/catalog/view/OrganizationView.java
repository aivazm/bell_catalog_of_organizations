package com.am.catalog.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * View-класс Organization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationView {
    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Короткое наименование
     */
    @Size(max = 50, message = "Наименование организации не должно превышать 50 символов")
    @NotEmpty(message = "Наименование организации не может быть пустым")
    private String name;

    /**
     * Полное наименование
     */
    @Size(max = 50, message = "Полное наименование организации не должно превышать 50 символов")
    @NotBlank(message = "Полное наименование организации не может быть пустым")
    private String fullName;

    /**
     * ИНН
     */
    @Size(max = 10, min = 10, message = "Длина ИНН 10 символов")
    @NotBlank(message = "ИНН не может быть пустым")
    private String inn;

    /**
     * КПП
     */
    @Size(max = 9, min = 9, message = "Длина КПП 9 символов")
    @NotBlank(message = "КПП не может быть пустым")
    private String kpp;

    /**
     * Адрес
     */
    @Size(max = 100, message = "Адрес должен уместиться в 100 символов")
    @NotBlank(message = "Address cannot be empty")
    private String address;

    /**
     * Телофон
     */
    @Size(max = 20, message = "Номер телефона не должен превышать 20 символов")
    private String phone;

    /**
     * Активность
     */
    private Boolean isActive;

    /**
     * Поле для вывода сообщения об успешном выполнении запросов /organization/save и /organization/update
     */
    private String result;

    public OrganizationView() {
    }

    /**
     * Конструктор для вывода данных после успешного выполнения запроса /organization/list
     * @param id
     * @param name
     * @param isActive
     */
    public OrganizationView(Long id, String name, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    /**
     * Конструктор для вывода данных после успешного выполнения запроса /organization/{id}
     * @param id
     * @param name
     * @param fullName
     * @param inn
     * @param kpp
     * @param address
     * @param phone
     * @param isActive
     */
    public OrganizationView(Long id,
                            String name,
                            String fullName,
                            String inn,
                            String kpp,
                            String address,
                            String phone,
                            Boolean isActive) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
    }

    /**
     * Конструктор для вывода сообщения об успешном выполнении запросов /organization/save и /organization/update
     * @param result
     */
    public OrganizationView(String result) {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
