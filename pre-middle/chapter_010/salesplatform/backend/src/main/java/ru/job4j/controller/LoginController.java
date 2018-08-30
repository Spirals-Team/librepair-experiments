package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.Tokenizer;
import ru.job4j.json.AuthJson;
import ru.job4j.model.User;
import ru.job4j.service.UserService;

import javax.naming.AuthenticationException;

/**
 * Signin controller.
 */
@RestController
public class LoginController {

    @Autowired
    private UserService service;

    @PostMapping(value = "/login",
        consumes = "application/json;charset=UTF-8",
        produces = "application/json;charset=UTF-8")
    public AuthJson login(@RequestBody User guest) throws AuthenticationException {
        if (!service.isCredential(guest.getEmail(), guest.getPassw())) {
            throw new AuthenticationException(
                String.format("User: %s is unknown.", guest.getEmail()));
        }
        User user = service.findByEmailPassw(guest.getEmail(), guest.getPassw());
        return new AuthJson(
            new Tokenizer().codeEncodeToken(user.getEmail()), user.getId(), 300L);
    }
}