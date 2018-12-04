package com.am.catalog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class OrganizationRequest {

    private Long id;

    @Size(max = 50)
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Size(max = 50)
    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    @Size(max = 10, min = 10)
    @NotBlank(message = "INN cannot be empty")
    private String inn;

    @Size(max = 9, min = 9)
    @NotBlank(message = "KPP cannot be empty")
    private String kpp;

    @Size(max = 100)
    @NotBlank(message = "Address cannot be empty")
    private String address;

    @Size(max = 20)
    private String phone;

    private Boolean isActive;

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
}
