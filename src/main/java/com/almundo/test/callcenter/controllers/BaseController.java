package com.almundo.test.callcenter.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseController {

    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.OK);
    }
}
