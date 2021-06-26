package com.am.catalog.view;

/**
 * Класс объекта, возвращаемого при успешном выполнении методов save, update
 */
public class SuccessResponse {
    private final static String RESULT = "success";

    public String getResult() {
        return RESULT;
    }
}
