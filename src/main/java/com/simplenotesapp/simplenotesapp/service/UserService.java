package com.simplenotesapp.simplenotesapp.service;

import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public User save(final User user) {
        User saved = userRepository.save(user);
        saved.getNotes().forEach(note -> note.addUser(saved));
        return saved;
    }

    @Transactional
    public void delete(final User user) {
        userRepository.delete(user);
    }

    @Transactional
    public User update(final User user) {
        User updatedUser = findOneById(user.getId());

        updatedUser.setName(user.getName());
        updatedUser.setSurname(user.getSurname());
        updatedUser.setLogin(user.getLogin());
        updatedUser.setPassword(user.getPassword());
        updatedUser.getNotes().forEach(note -> note.removeUser(updatedUser));
        updatedUser.getNotes().clear();
        updatedUser.setNotes(user.getNotes());
        updatedUser.getNotes().forEach(note -> note.addUser(updatedUser));

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOneById(final Long id) {
        return userRepository.findOneById(id).get();
    }

    public List<User> findAllById(final Set<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public User findOneByLogin(final String login) {
        return userRepository.findOneByLogin(login).get();
    }

    public User findOneByIdNullable(final Long id) {
        return userRepository.findOneById(id).orElse(null);
    }

    public Set<User> findAllByName(final String name) {
        return userRepository.findAllByName(name);
    }

    public Set<User> findAllBySurname(final String surname) {
        return userRepository.findAllBySurname(surname);
    }
}
