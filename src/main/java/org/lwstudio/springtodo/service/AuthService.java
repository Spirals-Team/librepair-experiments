package org.lwstudio.springtodo.service;

import org.lwstudio.springtodo.security.JwtAuthenticationRequest;
import org.lwstudio.springtodo.model.entity.User;

import org.lwstudio.springtodo.exception.ValidationException;

public interface AuthService {

    String login(JwtAuthenticationRequest jwtAuthenticationRequest) throws ValidationException;

    User register(JwtAuthenticationRequest jwtAuthenticationRequest) throws ValidationException;

    String refresh(String oldToken);
}
