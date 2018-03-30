package com.codesquad.library.domain.exceptions.model;

import com.codesquad.library.domain.exceptions.ApplicationException;
import com.codesquad.library.domain.exceptions.ErrorCodes;
import com.codesquad.library.domain.exceptions.ExceptionTypes;
import com.codesquad.library.dtos.exceptions.ExceptionsDocument;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseModelException extends ApplicationException {

    private ErrorCodes code = ErrorCodes.INTERNAL_ERROR;
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private String message;

    public BaseModelException(String message) {
        super(message);
        this.message = message;
    }

    public BaseModelException(ErrorCodes code, String message, HttpStatus status) {
        super(code, status, message);
        this.message = message;
        this.code = code;
        this.status = status;
    }
}
