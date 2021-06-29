package com.am.catalog.exception;

public final class ErrorMessage {

    public static final String EMPTY_ID_FIELD = "Поле id является обязательным";
    public static final String INVALID_ID_FIELD = "Некорректный идентификатор '%s'";
    public static final String EMPTY_OFFICE_ID_FIELD = "Поле officeId является обязательным";
    public static final String INVALID_OFFICE_ID_FIELD = "Некорректное значение поля officeId '%s'";
    public static final String EMPTY_ORG_ID_FIELD = "Поле orgId является обязательным";
    public static final String INVALID_ORG_ID_FIELD = "Некорректное значение поля orgId '%s'";
    public static final String EMPTY_NAME_FIELD = "Поле name является обязательным;";
    public static final String UPDATE_FAILED = "Обновление не удалось;";
    public static final String NO_OFFICE = "Офис с указанным id отсутствует";
    public static final String NO_ORGANIZATION = "Организация с указанным id отсутствует";
    public static final String EMPTY_DOC_FIELDS_WHEN_SAVE_USER = "При добавлении работника c документом поля DocCode, DocName, DocNumber, DocDate обязательны к заполнению";
    public static final String EMPTY_DOC_FIELDS_WHEN_UPDATE_USER = "При обновлении документа работника поля DocName, DocNumber, DocDate обязательны к заполнению";



    private ErrorMessage() {
    }
}
