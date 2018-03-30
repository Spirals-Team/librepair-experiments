package com.codesquad.library.domain.exceptions;

import lombok.Getter;

import java.util.Arrays;

import static com.codesquad.library.domain.exceptions.ErrorCodes.NoCodeFoundException.STD_ERROR_MSG;

@Getter
public enum ErrorCodes {

    NOT_FOUND(10, "ITEM NOT FOUND"),
    NOT_MATCH(11, "ITEM REQUESTED DOES NOT MATCH"),
    INTERNAL_ERROR(20, "INTERNAL ERROR OCCURRED"),
    SECURITY_ERROR(30, "SECURITY ERROR OCCURRED"),
    SECURITY_CREDENTIALS_ERROR(31, "PROVIDED CREDENTIALS DOES NOT MATCH"),
    SECURITY_FORBIDDEN_ERROR(32, "YOU ARE NOT ALLOWED TO ACCESS THIS RESOURCE.");

    public int code;
    public String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCodes findByCode(int code) {
        return Arrays.stream(ErrorCodes.values()).filter(c -> c.isMatchingCode(code)).findFirst().orElseThrow(() -> new NoCodeFoundException(STD_ERROR_MSG));
    }

    private boolean isMatchingCode(int code) {
        return code == this.code;
    }

    static class NoCodeFoundException extends ApplicationException {

        public static final String STD_ERROR_MSG = "조건에 맞는 코드가 검색되지 않았습니다.";

        NoCodeFoundException(String message) {
            super(message);
        }
    }
}
