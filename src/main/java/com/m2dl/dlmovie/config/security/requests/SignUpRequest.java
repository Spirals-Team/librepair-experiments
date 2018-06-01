package com.m2dl.dlmovie.config.security.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonDeserialize(using = SignUpRequestDeserializer.class)
public class SignUpRequest {

    @NotNull
    @Size(min = 5)
    private final String pseudo;

    @NotNull
    @Size(max = 40)
    private final String email;

    @NotNull
    @Size(min = 6)
    private final String password;

    public SignUpRequest(String pseudo, String email, String password) {
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPseudo() {
        return pseudo;
    }


}