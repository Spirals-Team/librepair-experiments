package com.m2dl.dlmovie.users.controllers;

import com.m2dl.dlmovie.users.domain.User;
import com.m2dl.dlmovie.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User register(User user) {
        user.setId(1L);
        return user;
    }
}
