package org.lwstudio.springtodo.repository;

import org.lwstudio.springtodo.model.entity.User;

import java.util.List;

public interface UserRepository {

    List<User> selectAllUsers();

    User selectUserById(Long id);

    User selectUserByUsername(String username);

    Integer insertUser(User user);

    Integer updateUserById(User user);

    Integer deleteUserById(Long id);

}
