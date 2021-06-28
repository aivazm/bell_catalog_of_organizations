package com.am.catalog.util;

import com.am.catalog.view.View;

/**
 * Сервис для валидации
 */
public interface ValidationService {

    /**
     * Валидация объекта View
     * @param view валидируемый объект
     */
    void validate(final View view);
}
