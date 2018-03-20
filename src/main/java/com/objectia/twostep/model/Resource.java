package com.objectia.twostep.model;

public class Resource {
    protected final int status;
    protected final boolean success;
    protected final String message;
    protected final int code;

    public Resource() {
        this.message = "";
        this.status = 400;
        this.code = 0;
        this.success = false;
    }

    public Resource(String message) {
        this.message = message;
        this.status = 400;
        this.code = 0;
        this.success = false;
    }

    public Resource(String message, int status) {
        this.message = message;
        this.status = status;
        this.code = 0;
        this.success = false;
    }

    public Resource(String message, int status, int code) {
        this.message = message;
        this.status = status;
        this.code = code;
        this.success = false;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

    public boolean getSuccess() {
        return this.success;
    }
}
