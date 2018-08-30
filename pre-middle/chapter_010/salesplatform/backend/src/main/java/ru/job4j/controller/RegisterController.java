package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.json.UserJson;
import ru.job4j.model.Role;
import ru.job4j.model.User;
import ru.job4j.service.UserService;

/**
 * Register controller.
 */
@RestController
public class RegisterController {

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserService service;

    @PostMapping(value = "/register",
        consumes = "application/json;charset=UTF-8")
    public void register(@RequestBody UserJson json) {
        User user = json.getUser();
        user.setRole(new Role().setId(1L));
        user.setPassw(encoder.encode(user.getPassw()));
        service.create(user);
    }
}
