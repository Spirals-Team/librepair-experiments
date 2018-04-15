package com.isa.instaticketapi.service.dto.account;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

/**
 * DTO Representing object with user's email
 */
public class RequestPasswordDTO {

    @Email
    @Size(min = 5, max = 100)
    private String email;

    public RequestPasswordDTO() {

    }

    public RequestPasswordDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
