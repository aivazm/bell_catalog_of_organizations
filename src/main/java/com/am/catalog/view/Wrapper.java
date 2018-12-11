package com.am.catalog.view;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public class Wrapper {
    private Object data;

    /**
     * Конструктор для ResponseWrapperController
     * @param data
     */
    public Wrapper(Object data) {
        this.data = data;
    }

    /**
     * Конструктор для тестирования
     */
    public Wrapper() {
    }

    /**
     * Геттер для тестирования
     * @return
     */
    public Object getData() {
        return data;
    }
}
