package com.objectia.twostep.model;

import com.google.gson.annotations.SerializedName;

public class User {
    protected final String id;
    protected final String email;
    protected final String phone;
    @SerializedName("country_code")
    protected final int countryCode;
    protected final boolean registered;
    protected final boolean confirmed;
    
    public User() {
        this.id = null;
        this.email = null;
        this.phone = null;
        this.countryCode = 0;
        this.registered = false;
        this.confirmed = false;
    }

    public String getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public int getCountryCode() {
        return this.countryCode;
    }

    public boolean getRegistered() {
        return this.registered;
    }

    public boolean getConfirmed() {
        return this.confirmed;
    }
}
