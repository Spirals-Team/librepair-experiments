package com.rexam.security.service;

public interface SecurityService {
    String findLoggedInEmail();

    void autologin(String email, String password);
}
