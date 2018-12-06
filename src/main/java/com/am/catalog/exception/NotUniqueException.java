package com.am.catalog.exception;

/**
 * Исключение NotUniqueException. Выбрасывается при попытке добавить дублирующие данные
 */
public class NotUniqueException extends RuntimeException {

    public NotUniqueException(String message) {
        super(message);
    }
}
