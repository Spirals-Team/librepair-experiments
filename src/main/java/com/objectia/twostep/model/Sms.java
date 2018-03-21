package com.objectia.twostep.model;

public class Sms {
    private final boolean ignored;
    private final String phone;

    public Sms() {
        this.ignored = false;
        this.phone = null;
    }

    public boolean getIgnored() {
        return this.ignored;
    }

    public String getPhone() {
        return this.phone;
    }
}
