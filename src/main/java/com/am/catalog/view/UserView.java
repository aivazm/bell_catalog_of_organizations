package com.am.catalog.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
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

    private Long officeId;
}
