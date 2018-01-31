package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;

import java.util.Collection;

/** @author danis.tazeev@gmail.com */
public interface UserRepository {
    /**
     * @throws NullPointerException if...
     * @throws IllegalArgumentException if...
     */
    User add(User user) throws DuplicateException;
    /**
     * @throws NullPointerException if...
     * @throws IllegalArgumentException if...
     */
    User update(User user) throws NotFoundException, DuplicateException;
    void remove(int id) throws NotFoundException;
    User get(int id) throws NotFoundException;
    User getByEmail(String email) throws NotFoundException;
    Collection<User> list();
    boolean atMostOneAdminLeft();
}
