package com.gianhaack.desafiocontaazul.service.exceptions;

public class InvalidBankslipProvidedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidBankslipProvidedException(String message) {
        super(message);
    }

    public InvalidBankslipProvidedException(String message, Throwable cause) {
        super(message, cause);
    }
}
