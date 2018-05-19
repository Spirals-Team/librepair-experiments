package org.lwstudio.springtodo.service;

import org.lwstudio.springtodo.model.dto.UserDTO;
import org.lwstudio.springtodo.model.entity.User;

import org.lwstudio.springtodo.exception.ValidationException;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    boolean saveUser(UserDTO UserDTO) throws ValidationException;

    boolean modifyUserById(UserDTO UserDTO) throws ValidationException;

    boolean deleteUserById(Long id);

}
