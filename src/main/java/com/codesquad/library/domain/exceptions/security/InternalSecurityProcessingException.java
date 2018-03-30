package com.codesquad.library.domain.exceptions.security;

import com.codesquad.library.domain.exceptions.ErrorCodes;
import org.springframework.http.HttpStatus;

public class InternalSecurityProcessingException extends BaseSecurityException {

    public InternalSecurityProcessingException(ErrorCodes code, HttpStatus status, String message) {
        super(code, status, message);
    }

    public InternalSecurityProcessingException() {
        super(ErrorCodes.SECURITY_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, "인증 서버에서 오류가 발생했습니다.");
    }
}
