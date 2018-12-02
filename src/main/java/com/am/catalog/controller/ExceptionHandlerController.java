package com.am.catalog.controller;

import com.am.catalog.dto.responses.CrudOperationRs;
import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.exception.NoObjectException;
import com.am.catalog.exception.NotUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<CrudOperationRs> emptyFieldException(EmptyFieldException ex) {
        CrudOperationRs crudOperationRs = new CrudOperationRs();
        crudOperationRs.setError(ex.getMessage());
        return new ResponseEntity<>(crudOperationRs, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotUniqueException.class)
    public ResponseEntity<CrudOperationRs> notUniqueException(NotUniqueException ex) {
        CrudOperationRs crudOperationRs = new CrudOperationRs();
        crudOperationRs.setError(ex.getMessage());
        return new ResponseEntity<>(crudOperationRs, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoObjectException.class)
    public ResponseEntity<CrudOperationRs> noOrganizationException(NoObjectException ex) {
        CrudOperationRs crudOperationRs = new CrudOperationRs();
        crudOperationRs.setError(ex.getMessage());
        return new ResponseEntity<>(crudOperationRs, HttpStatus.BAD_REQUEST);
    }
}
