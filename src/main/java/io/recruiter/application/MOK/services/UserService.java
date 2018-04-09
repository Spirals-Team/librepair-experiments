package io.recruiter.application.MOK.services;


import io.recruiter.application.common.database.model.AuthorityName;
import io.recruiter.application.common.database.model.User;
import io.recruiter.application.common.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static io.recruiter.application.common.ErrorMessages.USER_DOES_NOT_EXIST;
import static io.recruiter.application.common.ErrorMessages.USERNAME_ALREADY_EXISTS;

/**
 * Service with operations on Users.
 */
@Service
public class UserService {


    private static BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;


    public void create(User user, AuthorityName authority) {
        if (!userRepository.findByUsername(user.getUsername()).isPresent()) {
            user.getAuthorities().add(authority);
            user.setLastPasswordResetDate(new Date());
            user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException(USERNAME_ALREADY_EXISTS);
        }
    }

    public void delete(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User editedUser = optionalUser.get();
            userRepository.delete(editedUser);
        } else {
            throw new IllegalArgumentException(USER_DOES_NOT_EXIST);
        }
    }

    public void promote(String username, AuthorityName authority) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User editedUser = optionalUser.get();
            if (!editedUser.getAuthorities().contains(authority)) {
                editedUser.setAuthorities(Arrays.asList(authority));
                userRepository.save(editedUser);
            }
        } else {
            throw new IllegalArgumentException(USER_DOES_NOT_EXIST);
        }
    }

    public void removeAuthority(String username, AuthorityName authority) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User editedUser = optionalUser.get();
            if (editedUser.getAuthorities().contains(authority)) {
                editedUser.getAuthorities().remove(AuthorityName.ROLE_EDITOR);
                userRepository.save(editedUser);
            }
        } else {
            throw new IllegalArgumentException(USER_DOES_NOT_EXIST);
        }
    }

    public void edit(User user) {
        Optional<User> optionalUser = findByUsername(user.getUsername());
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException(USER_DOES_NOT_EXIST);
        }
        User editedUser = optionalUser.get();
        editedUser.setFirstname(user.getFirstname());
        editedUser.setLastname(user.getLastname());
        editedUser.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        editedUser.setEmail(user.getEmail());
        userRepository.save(editedUser);
    }

    public Optional<List<User>> getAllByAuthority(AuthorityName authorityName) {
        return userRepository.findAllByAuthorities(authorityName);
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByAuthoritiesAndUsername(AuthorityName roleEditor, String username) {
        return userRepository.findByAuthoritiesAndUsername(roleEditor, username);
    }
}
