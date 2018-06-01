package com.m2dl.dlmovie.config.security.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;

@JsonDeserialize(using = LoginRequestDeserializer.class)
public class LoginRequest {
    @NotNull
    private final String usernameOrEmail;

    @NotNull
    private final String password;

    public LoginRequest(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

}
