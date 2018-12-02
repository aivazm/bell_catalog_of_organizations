package com.am.catalog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class OfficeRq {

    private Long id;

    @Size(max = 50)
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Size(max = 100)
    @NotBlank(message = "Address cannot be empty")
    private String address;

    @Size(max = 20)
    private String phone;

    private Boolean isActive;

    private Long orgId;

    public OfficeRq() {
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
}
