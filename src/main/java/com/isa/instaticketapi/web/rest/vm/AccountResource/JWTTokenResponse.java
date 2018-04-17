package com.isa.instaticketapi.web.rest.vm.AccountResource;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object to return as body in JWT Authentication.
 */
public class JWTTokenResponse {

    private String idToken;
    private String role;
    private String username;

    public JWTTokenResponse(String idToken,String username, String role) {
        this.idToken = idToken; this.username = username; this.role = role;
    }

    @JsonProperty("id_token")
    String getIdToken() {
        return idToken;
    }

    void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

