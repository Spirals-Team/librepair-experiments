package com.psl.integrador.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ApiError {

    private HttpStatus status;
    private List<String> errors;

    ApiError() {
        super();
    }

    ApiError(HttpStatus status) {
        this.status = status;
    }

    void addError(String error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(error);
    }

    List<String> getErrors() {
        return errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
