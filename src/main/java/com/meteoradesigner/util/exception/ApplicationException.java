package com.meteoradesigner.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
//TODO doc
public class ApplicationException extends RuntimeException {
    private final String msgCode;
    private final HttpStatus httpStatus;
    private final String msg;

    public ApplicationException(String msgCode, HttpStatus httpStatus, String msg) {
        super(String.format("Msg=%s", msg));
        this.msgCode = msgCode;
        this.httpStatus = httpStatus;
        this.msg = msg;
    }
}