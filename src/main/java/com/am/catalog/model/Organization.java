package com.am.catalog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * Организация
 */
@Entity
public class Organization implements Serializable {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Короткое наименование организации
     */
    @Column(length = 50, nullable = false)
    private String name;

    /**
     * Полное наименование организации
     */
    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    /**
     * ИНН организации
     */
    @Column(length = 10, nullable = false, unique = true)
    private String inn;

    /**
     * КПП организации
     */
    @Column(length = 9, nullable = false)
    private String kpp;

    /**
     * Адрес организации
     */
    @Column(length = 10, nullable = false)
    private String address;

    /**
     * Телефон организации
     */
    private String phone;

    /**
     * Активность организации
     */
    @Column(name = "is_active")
    private Boolean isActive;

    public Organization() {
    }

    public Organization(Long id) {
        this.id = id;
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

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
