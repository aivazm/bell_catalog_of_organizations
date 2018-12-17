package com.am.catalog.controller;

import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.exception.NotUniqueException;
import com.am.catalog.view.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Контроллер для перехвата исключений и обработки ошибок
 */
@RestControllerAdvice
public class ExceptionHandlerController {
    private static Logger log = Logger.getLogger(ExceptionHandlerController.class.getName());

    /**
     * Перехват исключения EmptyFieldException
     *
     * @param ex
     * @return объект класса ErrorResponse с сообщением исключения
     */
    @ExceptionHandler(EmptyFieldException.class)
    public ErrorResponse emptyFieldException(EmptyFieldException ex) {
        log.log(Level.SEVERE, ex.getClass().getSimpleName() + ": " + ex.getMessage(), ex);
        return new ErrorResponse(ex.getMessage());
    }

    /**
     * Перехват исключения NotUniqueException
     *
     * @param ex
     * @return объект класса ErrorResponse с сообщением исключения
     */
    @ExceptionHandler(NotUniqueException.class)
    public ErrorResponse notUniqueException(NotUniqueException ex) {
        log.log(Level.SEVERE, ex.getClass().getSimpleName() + ": " + ex.getMessage(), ex);
        return new ErrorResponse(ex.getMessage());
    }

    /**
     * Перехват исключения NoObjectException
     *
     * @param ex
     * @return объект класса ErrorResponse с сообщением исключения
     */
    @ExceptionHandler(NoObjectException.class)
    public ErrorResponse noObjectException(NoObjectException ex) {
        log.log(Level.SEVERE, ex.getClass().getSimpleName() + ": " + ex.getMessage(), ex);
        return new ErrorResponse(ex.getMessage());
    }

    /**
     * Перехват всех исключений Exception
     *
     * @param ex
     * @return объект класса ErrorResponse с сообщением
     */
    @ExceptionHandler(value = Exception.class)
    public ErrorResponse anyException(Exception ex) {
        log.log(Level.SEVERE, ex.getClass().getSimpleName() + ": " + ex.getMessage(), ex);
        return new ErrorResponse("Ошибка на сервере");
    }
}
