package com.am.catalog.controller;

import com.am.catalog.dto.responses.ErrorResponse;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.exception.NotUniqueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EmptyFieldException.class)
    public ErrorResponse emptyFieldException(EmptyFieldException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(NotUniqueException.class)
    public ErrorResponse notUniqueException(NotUniqueException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(ex.getMessage());
        return errorResponse;
    }


    @ExceptionHandler(NoObjectException.class)
    public ErrorResponse noOrganizationException(NoObjectException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(ex.getMessage());
        return errorResponse;
    }
}
