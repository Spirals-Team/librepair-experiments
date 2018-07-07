package com.gianhaack.desafiocontaazul.controller.exceptions;

import com.gianhaack.desafiocontaazul.service.exceptions.BankslipNotFoundException;
import com.gianhaack.desafiocontaazul.service.exceptions.BankslipNotProvidedException;
import com.gianhaack.desafiocontaazul.service.exceptions.InvalidBankslipProvidedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BankslipNotProvidedException.class)
    public ResponseEntity bankslipNotProvided(BankslipNotProvidedException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(InvalidBankslipProvidedException.class)
    public ResponseEntity invalidBankslipProvided(InvalidBankslipProvidedException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(BankslipNotFoundException.class)
    public ResponseEntity invalidBankslipProvided(BankslipNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}
