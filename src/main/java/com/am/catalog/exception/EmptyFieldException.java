package com.am.catalog.exception;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(String message) {
        super(message);
    }
}