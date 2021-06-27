package com.am.catalog.exception;

/**
 * Исключение EmptyFieldException. Выбрасывается при пустых или некорректных параметрах запроса.
 */
public class EmptyFieldException extends RuntimeException {

    /**
     * Конструктор исключения
     * @param message сообщение об ошибке
     */
    public EmptyFieldException(String message) {
        super(message);
    }
}