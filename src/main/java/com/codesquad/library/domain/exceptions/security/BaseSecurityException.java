package com.codesquad.library.domain.exceptions.security;

import com.codesquad.library.domain.exceptions.ApplicationException;
import com.codesquad.library.domain.exceptions.ErrorCodes;
import com.codesquad.library.dtos.exceptions.ExceptionsDocument;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BaseSecurityException extends ApplicationException{

    private ErrorCodes code = ErrorCodes.SECURITY_ERROR;
    private HttpStatus status = HttpStatus.UNAUTHORIZED;
    private String message;

    public BaseSecurityException(ErrorCodes code, HttpStatus status, String message) {
        super(code, status, message);
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public BaseSecurityException(String message) {
        super(ErrorCodes.SECURITY_ERROR, HttpStatus.UNAUTHORIZED, message);
        this.message = message;
    }

}
