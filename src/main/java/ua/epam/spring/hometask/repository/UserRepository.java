package ua.epam.spring.hometask.repository;

import ua.epam.spring.hometask.domain.User;

import java.util.Collection;

/**
 * Created by Vladimir on 09.10.2017.
 */
public interface UserRepository {
    User save(User user);
    boolean delete(User user);
    User get(long id);
    Collection<User> getAll();
}
