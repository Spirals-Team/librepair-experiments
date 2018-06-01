package com.m2dl.dlmovie.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DBException extends RuntimeException {
    public DBException(String message) {
        super(message);
    }
}