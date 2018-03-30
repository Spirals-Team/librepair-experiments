package com.codesquad.library.domain.exceptions.security;

import com.codesquad.library.domain.exceptions.ErrorCodes;
import org.springframework.http.HttpStatus;

public class NoAccountPresentException extends BaseSecurityException{

    public NoAccountPresentException(ErrorCodes code, HttpStatus status, String message) {
        super(code, status, message);
    }

    public NoAccountPresentException(String message) {
        super(ErrorCodes.SECURITY_ERROR, HttpStatus.NOT_FOUND, message);
    }
}
