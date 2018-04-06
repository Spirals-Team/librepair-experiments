package com.m2dl.dlmovie.users.services;

import com.m2dl.dlmovie.users.domain.User;
import com.m2dl.dlmovie.users.repositories.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService() {

    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }
}
