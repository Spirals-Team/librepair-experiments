package com.mytaxi.apis.phrase.api.exception;

public class ApiExecutionException extends RuntimeException
{
    public ApiExecutionException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
