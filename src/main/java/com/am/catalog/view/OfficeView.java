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
 * View-класс Office
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OfficeView {
    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Наименование
     */
    @Size(max = 50, message = "Наименование офиса не должно превышать 50 символов")
    @NotEmpty(message = "Наименование офиса не может быть пустым")
    private String name;

    /**
     * Адрес
     */
    @Size(max = 100, message = "Адрес не должен превышать 100 символов")
    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

    /**
     * Телефон
     */
    @Size(max = 20, message = "Номер телефона не должен превышать 20 символов")
    private String phone;

    /**
     * Активность
     */
    private Boolean isActive;

    /**
     * Id организации офиса
     */
    private Long orgId;
}
