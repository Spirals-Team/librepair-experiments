package com.codesquad.library.domain.exceptions.model;

import com.codesquad.library.domain.exceptions.ErrorCodes;
import org.springframework.http.HttpStatus;

public class NoBookExistsException extends BaseModelException {

    public NoBookExistsException(String message) {
        super(ErrorCodes.NOT_FOUND, message, HttpStatus.NOT_FOUND);
    }
}
