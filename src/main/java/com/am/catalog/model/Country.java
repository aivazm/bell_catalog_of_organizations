package com.am.catalog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Справочник государств. Состоит из кода и наименования стран
 */
@Entity
public class Country {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Трехзначный цифровой код государства
     */
    @Column(length = 3, nullable = false)
    private String code;

    /**
     * Наименование государства
     */
    @Column(length = 50, nullable = false)
    private String name;

    public Country() {
    }

    public Country(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
