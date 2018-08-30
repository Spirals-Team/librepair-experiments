package ru.job4j.service;

import ru.job4j.model.User;

public interface UserService {

    User findByEmailPassw(String email, String passw);

    User findByEmail(String email);

    boolean isCredential(String email, String passw);

    Long create(User user);
}
