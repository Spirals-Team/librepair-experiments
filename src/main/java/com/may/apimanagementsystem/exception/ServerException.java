package com.may.apimanagementsystem.exception;

public class ServerException extends RuntimeException {

    private static String EX_STRING = "服务器错误";

    public ServerException(){
        super(EX_STRING);
    }
}
