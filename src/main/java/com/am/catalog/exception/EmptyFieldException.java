package com.am.catalog.exception;

/**
 * Исключение EmptyFieldException. Выбрасывается при пустых или некорректных параметрах запроса.
 */
public class EmptyFieldException extends RuntimeException {

    /**
     * Конструктор исключения
     * @param message
     */
    public EmptyFieldException(String message) {
        super(message);
    }
}