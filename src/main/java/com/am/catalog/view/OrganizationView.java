package com.am.catalog.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * View-класс Organization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrganizationView implements View {
    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Короткое наименование
     */
    @Size(max = 50, message = "Наименование организации не должно превышать 50 символов")
    @NotEmpty(message = "Наименование организации не может быть пустым")
    private String name;

    /**
     * Полное наименование
     */
    @Size(max = 50, message = "Полное наименование организации не должно превышать 50 символов")
    @NotBlank(message = "Полное наименование организации не может быть пустым")
    private String fullName;

    /**
     * ИНН
     */
    @Size(max = 10, min = 10, message = "Длина ИНН 10 символов")
    @NotBlank(message = "ИНН не может быть пустым")
    private String inn;

    /**
     * КПП
     */
    @Size(max = 9, min = 9, message = "Длина КПП 9 символов")
    @NotBlank(message = "КПП не может быть пустым")
    private String kpp;

    /**
     * Адрес
     */
    @Size(max = 100, message = "Адрес должен уместиться в 100 символов")
    @NotBlank(message = "Address cannot be empty")
    private String address;

    /**
     * Телофон
     */
    @Size(max = 20, message = "Номер телефона не должен превышать 20 символов")
    private String phone;

    /**
     * Активность
     */
    private Boolean isActive;

}
