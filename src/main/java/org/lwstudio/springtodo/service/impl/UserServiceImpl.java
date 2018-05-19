package org.lwstudio.springtodo.service.impl;

import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.model.dto.UserDTO;
import org.lwstudio.springtodo.repository.UserRepository;
import org.lwstudio.springtodo.repository.TodoRepository;
import org.lwstudio.springtodo.service.UserService;

import org.lwstudio.springtodo.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.selectAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.selectUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.selectUserByUsername(username);
    }

    @Override
    @Transactional
    public boolean saveUser(UserDTO userDTO) throws ValidationException {
        if (this.usernameExists(userDTO.getUsername())) {
            throw new ValidationException("Username already exists.");
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());

        try {
            User user = new User(
                userDTO.getUsername(),
                encryptedPassword
            );

            return userRepository.insertUser(user) > 0;
        } catch (ConstraintViolationException e) {
            throw new ValidationException(e.getConstraintViolations().iterator().next().getMessage());
        }
    }

    @Override
    public boolean modifyUserById(UserDTO userDTO) throws ValidationException {
        User user = userRepository.selectUserById(userDTO.getId());

        boolean sameUsername = user.getUsername().equals(userDTO.getUsername());

        if (!sameUsername && this.usernameExists(userDTO.getUsername())) {
            throw new ValidationException("New username already been used.");
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());

        try {
            user.setUsername(userDTO.getUsername());
            user.setPassword(encryptedPassword);

            return userRepository.updateUserById(user) > 0;
        } catch (ConstraintViolationException e) {
            throw new ValidationException(e.getConstraintViolations().iterator().next().getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteUserById(Long id) {
        todoRepository.deleteTodosByUserId(id);

        return userRepository.deleteUserById(id) > 0;
    }

    public boolean usernameExists(String username) {
        User user = userRepository.selectUserByUsername(username);

        return user != null;
    }
}
