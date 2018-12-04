package com.am.catalog.dto.responses;

public class SuccessResponse {
    private String result;

    public SuccessResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}