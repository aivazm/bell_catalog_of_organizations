package com.am.catalog.view;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Класс-обертка. Весь OUT, за исключением ошибок
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public class Wrapper<T> {
    private final T data;

    /**
     * Конструктор для ResponseWrapperController
     * @param data обертываемый объект
     */
    public Wrapper(T data) {
        this.data = data;
    }

    /**
     * Конструктор для тестирования
     */
    public Wrapper() {
        data = null;
    }

    /**
     * Геттер для тестирования
     * @return
     */
    public T getData() {
        return data;
    }
}
