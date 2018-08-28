package com.roldukhine.entity;

public interface UserService {
    void addUser(User user);

    User getUser(long id);

    void updateUser(User user);

    void deleteUser(User user);

    User checkUser(String login, String password);
}
