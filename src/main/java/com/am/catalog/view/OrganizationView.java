package com.am.catalog.view;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class OrganizationView {

    public Long id;

    @Size(max = 50)
    @NotEmpty(message = "Name cannot be empty")
    public String name;

    @Size(max = 50)
    @NotBlank(message = "Full name cannot be empty")
    @Column(name = "full_name")
    public String fullName;

    @Size(max = 10, min=10)
    @NotBlank(message = "INN cannot be empty")
    public String inn;

    @Size(max = 9, min=9)
    @NotBlank(message = "KPP cannot be empty")
    public String kpp;

    @Size(max = 100)
    @NotBlank(message = "Address cannot be empty")
    public String address;

    @Size(max = 20)
    public String phone;

    public Boolean isActive;
}
