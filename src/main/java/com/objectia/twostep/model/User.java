package com.objectia.twostep.model;

import com.google.gson.annotations.SerializedName;

public class User {
    private final String id;
    private final String email;
    private final String phone;
    @SerializedName("country_code")
    private final int countryCode;
    private final boolean registered;
    private final boolean confirmed;
    
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
