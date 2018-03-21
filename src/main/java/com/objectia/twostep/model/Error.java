package com.objectia.twostep.model;

public class Error {
    private final int status;
    private final String message;
    private final boolean success;

    public Error() {
        this.status = 0;
        this.message = null;
        this.success = false;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean getSuccess() {
        return this.success;
    }
}
