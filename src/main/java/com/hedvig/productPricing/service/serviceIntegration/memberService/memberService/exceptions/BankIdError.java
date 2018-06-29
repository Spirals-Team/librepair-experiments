package com.hedvig.productPricing.service.serviceIntegration.memberService.memberService.exceptions;

public class BankIdError extends RuntimeException {
    private final ErrorType errorType;
    private final String message;

    public BankIdError(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }


    public ErrorType getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }
}
