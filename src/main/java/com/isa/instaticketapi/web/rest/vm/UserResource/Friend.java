package com.isa.instaticketapi.web.rest.vm.UserResource;

public class Friend {

    private String username;
    private String email;

    public Friend() { }

    public Friend(String username, String email){
        this.email = email;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
