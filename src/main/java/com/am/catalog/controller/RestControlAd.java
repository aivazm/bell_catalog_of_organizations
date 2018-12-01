package com.am.catalog.controller;

import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoOrganizationException;
import com.am.catalog.exception.NotUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControlAd {

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<String> emptyFieldException(EmptyFieldException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotUniqueException.class)
    public ResponseEntity<String> notUniqueException(NotUniqueException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoOrganizationException.class)
    public ResponseEntity<String> noOrganizationException(NoOrganizationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
