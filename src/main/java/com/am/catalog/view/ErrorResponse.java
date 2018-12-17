package com.am.catalog.view;

/**
 * Класс с полем error. Все сообщения об ошибках возвращаются через этот класс
 */
public class ErrorResponse {
    private final String error;

    /**
     * Конструктор для теста
     */
    public ErrorResponse() {
        error = null;
    }

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
