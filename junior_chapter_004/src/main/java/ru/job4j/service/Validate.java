package ru.job4j.service;

import ru.job4j.model.User;

import java.util.List;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 13.06.2018.
 * @version 1.0.
 * @since 0.1.
 */
public interface Validate {
    boolean addValidate(User user);

    boolean updateValidate(User user);

    boolean deleteValidate(int id);

    List<User> findAllValidate();

    boolean findByIdValidate(int id);

    boolean isCredentional(String login, String password);

    void close();
}
