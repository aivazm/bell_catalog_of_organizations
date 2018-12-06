package com.am.catalog.controller;

import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.exception.NotUniqueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Контроллер для перехвата исключений и обработки ошибок
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     * Перехват исключения EmptyFieldException
     * @param ex
     * @return
     */
    @ExceptionHandler(EmptyFieldException.class)
    public ErrorResponse emptyFieldException(EmptyFieldException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    /**
     * Перехват исключения NotUniqueException
     * @param ex
     * @return
     */
    @ExceptionHandler(NotUniqueException.class)
    public ErrorResponse notUniqueException(NotUniqueException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    /**
     * Перехват исключения NoObjectException
     * @param ex
     * @return
     */
    @ExceptionHandler(NoObjectException.class)
    public ErrorResponse noOrganizationException(NoObjectException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    /**
     * Класс с полем error. Все сообщения об ошибках возвращаются через этот класс
     */
    class ErrorResponse {
        private String error;
        private ErrorResponse(String error) {
            this.error = error;
        }

    }
}
