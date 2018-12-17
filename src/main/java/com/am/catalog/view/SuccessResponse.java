package com.am.catalog.view;

/**
 * Класс объекта, возвращаемого при успешном выполнении методов save, update
 */
public class SuccessResponse {
    private final String result;

    /**
     *
     * Конструктор
     * @param result
     */
    public SuccessResponse(String result) {
        this.result = result;
    }

    /**
     * Конструктор для теста
     */
    public SuccessResponse() {
        result = null;
    }

    public String getResult() {
        return result;
    }
}
