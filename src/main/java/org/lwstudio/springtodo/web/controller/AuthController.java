package org.lwstudio.springtodo.web.controller;

import org.lwstudio.springtodo.security.JwtAuthenticationRequest;
import org.lwstudio.springtodo.security.JwtAuthenticationResponse;

import org.lwstudio.springtodo.model.entity.User;

import org.lwstudio.springtodo.service.AuthService;

import org.springframework.security.core.AuthenticationException;
import org.lwstudio.springtodo.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Value("Authorization")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws ValidationException {
        final String token = authService.login(authenticationRequest);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @PostMapping("/register")
    public User register(@RequestBody JwtAuthenticationRequest authenticationRequest) throws ValidationException{
        return authService.register(authenticationRequest);
    }
}
