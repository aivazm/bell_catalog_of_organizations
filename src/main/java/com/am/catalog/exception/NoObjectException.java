package com.am.catalog.exception;

/**
 * Исключение NoObjectException. Выбрасывается попытке получения несуществующего объекта
 */
public class NoObjectException extends RuntimeException {
    public NoObjectException(String message) {
        super(message);
    }
}
