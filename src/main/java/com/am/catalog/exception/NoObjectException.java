package com.am.catalog.exception;

/**
 * Исключение NoObjectException. Выбрасывается попытке получения несуществующего объекта
 */
public class NoObjectException extends RuntimeException {

    /**
     * Конструктор исключения
     * @param message
     */
    public NoObjectException(String message) {
        super(message);
    }
}
