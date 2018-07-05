package com.ljd.hackajob.phonebook.model;

public class ExceptionResponse {
    private final String errorCode;
    private final String errorMessage;
    
    public ExceptionResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
