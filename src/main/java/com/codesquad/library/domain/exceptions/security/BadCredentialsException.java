package com.codesquad.library.domain.exceptions.security;

import com.codesquad.library.domain.exceptions.ErrorCodes;
import org.springframework.http.HttpStatus;

public class BadCredentialsException extends BaseSecurityException {

    public BadCredentialsException(ErrorCodes code, HttpStatus status, String message) {
        super(code, status, message);
    }

    public BadCredentialsException() {
        super(ErrorCodes.SECURITY_CREDENTIALS_ERROR, HttpStatus.FORBIDDEN, "아이디나 암호가 일치하지 않습니다.");
    }
}
