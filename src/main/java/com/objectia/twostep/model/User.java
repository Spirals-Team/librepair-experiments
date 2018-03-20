package com.objectia.twostep.model;

import com.google.gson.annotations.SerializedName;

public class User extends Resource {
    protected String email;
    protected String phone;
    @SerializedName("country_code")
    protected int countryCode;
    protected boolean registered;
    protected boolean confirmed;
    
    public User() {
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

    public boolean getCcnfirmed() {
        return this.confirmed;
    }
}
