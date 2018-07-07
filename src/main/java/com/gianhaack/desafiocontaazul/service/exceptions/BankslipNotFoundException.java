package com.gianhaack.desafiocontaazul.service.exceptions;

public class BankslipNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BankslipNotFoundException(String message) {
        super(message);
    }

    public BankslipNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}