package com.am.catalog.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * View-класс User
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserView {
    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Имя
     */
    @Size(max = 50, message = "Имя не должно превышать 50 символов")
    @NotEmpty(message = "Имя не может быть пустым")
    private String firstName;

    /**
     * Фамилия
     */
    @Size(max = 50, message = "Фамилия не должно превышать 50 символов")
    private String secondName;

    /**
     * Отчество
     */
    @Size(max = 50, message = "Отчество не должно превышать 50 символов")
    private String middleName;

    /**
     * Должность
     */
    @Size(max = 50, message = "Должность не должна превышать 50 символов")
    @NotEmpty(message = "Поле position не может быть пустым")
    private String position;

    /**
     * Телефон
     */
    @Size(max = 20, message = "Номер телефона не должен превышать 20 символов")
    private String phone;

    /**
     * Код типа документа. Двузначный цифровой код
     */
    @Size(min = 2, max = 2, message = "Код документа состоит из двух символов")
    private String docCode;

    /**
     * Наименование документа
     */
    @Size(max = 50, message = "Наименование документа не может превышать 50 символов")
    private String docName;

    /**
     * Номер документа
     */
    @Size(max = 20, message = "Номер документа не может превышать 20 символов")
    private String docNumber;

    /**
     * Дата документа
     */
    @PastOrPresent(message = "Дата документа должна быть прошлым или настоящим")
    private Date docDate;

    /**
     * Наименование государства
     */

    private String citizenshipName;

    /**
     * Код государства. Трехзначный цифровой код
     */
    @Size(min = 3, max = 3, message = "Код государства состоит из трех символов")
    private String citizenshipCode;

    /**
     * Идентификация
     */
    private Boolean isIdentified;

//    @Positive(message = "Office Id должен быть больше нуля")
//    @NotNull(message = "Поле Office Id не может быть пустым")
    private Long officeId;

    /**
     * Поле для вывода сообщения об успешном выполнении запросов /user/save и /user/update
     */
    private String result;

    public UserView() {
    }

    /**
     * Конструктор для вывода данных после успешного выполнения запроса /user/list
     * @param id
     * @param firstName
     * @param secondName
     * @param middleName
     * @param position
     */
    public UserView(Long id,
                    String firstName,
                    String secondName,
                    String middleName,
                    String position
    ) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.position = position;
    }

    /**
     * Конструктор для вывода данных после успешного выполнения запроса /user/{id}
     * @param id
     * @param firstName
     * @param secondName
     * @param middleName
     * @param position
     * @param phone
     * @param docName
     * @param docNumber
     * @param docDate
     * @param citizenshipName
     * @param citizenshipCode
     * @param isIdentified
     */
    public UserView(Long id,
                    String firstName,
                    String secondName,
                    String middleName,
                    String position,
                    String phone,
                    String docName,
                    String docNumber,
                    Date docDate,
                    String citizenshipName,
                    String citizenshipCode,
                    Boolean isIdentified
    ) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.position = position;
        this.phone = phone;
        this.docName = docName;
        this.docNumber = docNumber;
        this.docDate = docDate;
        this.citizenshipName = citizenshipName;
        this.citizenshipCode = citizenshipCode;
        this.isIdentified = isIdentified;
    }

    /**
     * Конструктор для вывода сообщения об успешном выполнении запросов /user/save и /user/update
     * @param result
     */
    public UserView(String result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getCitizenshipName() {
        return citizenshipName;
    }

    public void setCitizenshipName(String citizenshipName) {
        this.citizenshipName = citizenshipName;
    }

    public String getCitizenshipCode() {
        return citizenshipCode;
    }

    public void setCitizenshipCode(String citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    public Boolean isIdentified() {
        return isIdentified;
    }

    public void setIdentified(Boolean identified) {
        isIdentified = identified;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
