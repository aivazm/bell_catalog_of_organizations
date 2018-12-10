package com.am.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Офис
 */
@Entity
public class Office implements Serializable {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование офиса
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * Адрес офиса
     */
    @Column(name = "address", length = 100, nullable = false)
    private String address;

    /**
     * Телефон офиса
     */
    private String phone;

    /**
     * Активность офиса
     */
    @Column(name = "is_active")
    private Boolean isActive;

    /**
     * Организация, которой принадлежит офис.
     */
    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;

    public Office() {
    }

    public Long getId() {
        return id;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
