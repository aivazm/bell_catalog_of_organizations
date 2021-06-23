package com.am.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Работник. Связан с Документом, Государством, Офисом
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя работника
     */
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    /**
     * Фамилия работника
     */
    @Column(name = "second_name", length = 50)
    private String secondName;

    /**
     * Отчество работника
     */
    @Column(name = "middle_name", length = 50)
    private String middleName;

    /**
     * Должность
     */
    @Column(length = 50, nullable = false)
    private String position;

    /**
     * Телефон
     */
    @Column(length = 20)
    private String phone;

    /**
     * Документ. Связан с Entity Document
     */
    @OneToOne
    @JoinColumn(name = "doc_id")
    private Document document;

    /**
     * Государство. Связано с Entity Country
     */
    @ManyToOne
    @JoinColumn(name = "citizenship_id")
    private Country country;

    /**
     * Идентификация работника
     */
    private Boolean isIdentified;

    /**
     * Офис. Связан с Entity Office
     */
    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;
}
