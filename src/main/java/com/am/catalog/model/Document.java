package com.am.catalog.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Документ работника. Связан с Типом документа. Состоит из номера и даты документа
 */
@Entity
public class Document {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип документа
     */
    @ManyToOne
    @JoinColumn(name = "doc_type_id")
    private DocType docType;

    /**
     * Номер документа
     */
    @Column(name = "doc_number", length = 20, nullable = false)
    private String number;

    /**
     * Дата документа
     */
    @Column(name = "doc_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    public Document() {
    }

    public Document(DocType docType, String number, Date date) {
        this.docType = docType;
        this.number = number;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
