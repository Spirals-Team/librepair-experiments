package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.DuplicateException;
import ru.javawebinar.topjava.repository.NotFoundException;

import java.util.Collection;

public interface UserService {
    User register(User user) throws DuplicateException;
    void update(User user, User onBehalfOf) throws NotFoundException, DuplicateException;
    void remove(int userId, User onBehalfOf) throws NotFoundException;
    User get(int userId, User onBehalfOf) throws NotFoundException;
    User getByEmail(String email, User onBehalfOf) throws NotFoundException;
    Collection<User> list(User onBehalfOf);
}
