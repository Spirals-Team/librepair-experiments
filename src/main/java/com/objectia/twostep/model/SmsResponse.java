package com.objectia.twostep.model;

public class SmsResponse extends Response2 {
    private final boolean ignored;

    public SmsResponse() {
        super();
        this.ignored = false;
    }

    public SmsResponse(String message) {
        super(message);
        this.ignored = false;
    }

    public SmsResponse(String message, int status) {
        super(message, status);
        this.ignored = false;
    }

    public boolean getIgnored() {
        return this.ignored;
    }

}
