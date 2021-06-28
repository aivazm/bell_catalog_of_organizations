package com.am.catalog.util;

import com.am.catalog.exception.EmptyFieldException;
import com.am.catalog.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ValidationServiceImpl implements ValidationService{

    /** валидатор */
    private final Validator validator;

    @Autowired
    public ValidationServiceImpl(Validator validator) {
        this.validator = validator;
    }

    /**
     * Валидация объекта View
     * @param view валидируемый объект
     */
    public void validate(final View view) {
        Set<ConstraintViolation<View>> validate = validator.validate(view);
        if (validate.isEmpty()) {
            return;
        }
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<View> violation : validate) {
            message.append(violation.getMessage());
            message.append("; ");
        }
        throw new EmptyFieldException(message.toString().trim());
    }
}
