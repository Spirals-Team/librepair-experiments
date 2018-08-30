package ru.job4j.userapp;

import java.util.List;

public interface UserDao {

    User getUser(Integer id);

    List<User> getAllUsers();

    User getUserByUserLoginAndPassw(String login, String passw);

    boolean insertUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Integer id);
}
