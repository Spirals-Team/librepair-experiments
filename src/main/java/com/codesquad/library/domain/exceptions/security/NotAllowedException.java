package com.codesquad.library.domain.exceptions.security;

import com.codesquad.library.domain.exceptions.ErrorCodes;
import com.codesquad.library.dtos.exceptions.ExceptionsDocument;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class NotAllowedException extends OAuth2Exception {

    private final HttpStatus httpStatus = HttpStatus.FORBIDDEN;
    private final ErrorCodes errorCodes = ErrorCodes.SECURITY_FORBIDDEN_ERROR;

    public NotAllowedException(String message) {
        super(message);
    }

    public NotAllowedException(OAuth2Exception e) {
        super(e.getMessage());
    }

    public ExceptionsDocument generateDocument() {
        return new ExceptionsDocument(errorCodes.getCode(), errorCodes.getMessage(), this.httpStatus);
    }
}
