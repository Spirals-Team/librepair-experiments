package com.simplenotesapp.simplenotesapp.service;

import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User save(final User user) {
        return userRepository.save(user);
    }

    public void delete(final User user) {
        userRepository.delete(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOneById(final long id) {
        return userRepository.findOneById(id).get();
    }

    public List<User> findAllById(final Set<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public User findOneByLogin(final String login) {
        return userRepository.findOneByLogin(login).get();
    }

    public User findOneByIdNullable(final long id) {
        return userRepository.findOneById(id).orElse(null);
    }

    public Set<User> findAllByName(final String name) {
        return userRepository.findAllByName(name);
    }

    public Set<User> findAllBySurname(final String surname) {
        return userRepository.findAllBySurname(surname);
    }
}
