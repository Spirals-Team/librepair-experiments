package com.isa.instaticketapi.service.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

public class MailForgetDTO {


    @Email
    @NotNull
    private String email;

    public MailForgetDTO() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
