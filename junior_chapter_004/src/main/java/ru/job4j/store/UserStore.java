package ru.job4j.store;

import ru.job4j.model.User;

import java.util.List;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 13.06.2018.
 * @version 1.0.
 * @since 0.1.
 */
public interface UserStore {
    void addStore(User user);

    void updateStore(User user);

    boolean deleteStore(int id);

    List<User> findAllStore();

    User findByIdStore(int id);

    void closePoolConnections();

    boolean isCredentional(String login, String password);

}
