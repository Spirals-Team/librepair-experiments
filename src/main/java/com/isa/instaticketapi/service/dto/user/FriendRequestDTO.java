package com.isa.instaticketapi.service.dto.user;

/**
 * DTO representing email of person that we want to add
 */
public class FriendRequestDTO {

    public String email;

    public FriendRequestDTO() {

    }

    public FriendRequestDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
