package com.am.catalog.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CrudOperationRs {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public CrudOperationRs() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
