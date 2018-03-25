package com.rexam.security.service;

import com.rexam.model.User;

public interface UserService {
    void save(User user);

    User findByEmail(String email);
}
