package org.lwstudio.springtodo.service.impl;

import org.lwstudio.springtodo.security.JwtAuthenticationRequest;
import org.lwstudio.springtodo.security.JwtTokenUtil;
import org.lwstudio.springtodo.security.JwtUser;
import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.repository.UserRepository;
import org.lwstudio.springtodo.service.AuthService;

import org.lwstudio.springtodo.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.ConstraintViolationException;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("Bearer ")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(
                AuthenticationManager authenticationManager,
                UserDetailsService userDetailsService,
                JwtTokenUtil jwtTokenUtil,
                UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String login(JwtAuthenticationRequest jwtAuthenticationRequest) throws ValidationException {
        if (!this.usernameExists(jwtAuthenticationRequest.getUsername())) {
            throw new ValidationException("User Not exist.");
        }

        String username = jwtAuthenticationRequest.getUsername();
        String password = jwtAuthenticationRequest.getPassword();
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);

        return token;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.refreshToken(token);
    }

    @Override
    @Transactional
    public User register(JwtAuthenticationRequest jwtAuthenticationRequest) throws ValidationException {
        if (this.usernameExists(jwtAuthenticationRequest.getUsername())) {
            throw new ValidationException("Username already exists.");
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(jwtAuthenticationRequest.getPassword());

        try {
            User user = new User(
                jwtAuthenticationRequest.getUsername(),
                encryptedPassword
            );

            userRepository.insertUser(user);

            return userRepository.selectUserByUsername(jwtAuthenticationRequest.getUsername());
        } catch (ConstraintViolationException e) {
            throw new ValidationException(e.getConstraintViolations().iterator().next().getMessage());
        }
    }

    public boolean usernameExists(String username) {
        User user = userRepository.selectUserByUsername(username);

        return user != null;
    }
}
