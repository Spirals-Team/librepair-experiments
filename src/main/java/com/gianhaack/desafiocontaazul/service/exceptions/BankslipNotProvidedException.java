package com.gianhaack.desafiocontaazul.service.exceptions;

public class BankslipNotProvidedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BankslipNotProvidedException(String message) {
        super(message);
    }

    public BankslipNotProvidedException(String message, Throwable cause) {
        super(message, cause);
    }
}
