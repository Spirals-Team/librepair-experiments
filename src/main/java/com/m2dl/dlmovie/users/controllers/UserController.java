package com.m2dl.dlmovie.users.controllers;

import com.m2dl.dlmovie.config.security.CurrentUser;
import com.m2dl.dlmovie.config.security.JwtAuthenticationResponse;
import com.m2dl.dlmovie.config.security.UserPrincipal;
import com.m2dl.dlmovie.config.security.requests.LoginRequest;
import com.m2dl.dlmovie.config.security.requests.SignUpRequest;
import com.m2dl.dlmovie.users.domain.User;
import com.m2dl.dlmovie.users.exceptions.ActionNotAllowed;
import com.m2dl.dlmovie.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User register(@Valid @RequestBody SignUpRequest request) {
        return userService.register(
                new User(request.getPseudo(),
                        request.getEmail(),
                        request.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        String jwt = userService.login(loginRequest);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("/{id}")
    public User getUser(@CurrentUser UserPrincipal currentUser, @PathVariable("id") Long id) {
        if(currentUser == null || !currentUser.getId().equals(id)) throw new ActionNotAllowed();
        return userService.getById(id);
    }


}
