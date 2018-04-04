/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thekoalas.koalas;

/**
 *
 * @author GONTARD Benjamin
 */
public class NoColumnsException extends RuntimeException{

    public NoColumnsException() {
    }

    public NoColumnsException(String message) {
        super(message);
    }

    public NoColumnsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoColumnsException(Throwable cause) {
        super(cause);
    }

    public NoColumnsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
