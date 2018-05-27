package ru.job4j.ioc.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Component
public class UserStorage implements Storage<User> {
    private final Storage<User> storage;

    @Autowired
    public UserStorage(Storage<User> storage) {
        this.storage = storage;
    }

    @Override
    public boolean add(final User entity) {
        return this.storage.add(entity);
    }

    @Override
    public boolean del(final User entity) {
        return this.storage.del(entity);
    }

    @Override
    public boolean del(final int id) {
        return this.storage.del(id);
    }

    @Override
    public boolean find(final User entity) {
        return this.storage.find(entity);
    }

    @Override
    public User find(String name) {
        return this.storage.find(name);
    }

    @Override
    public User find(final int id) {
        return this.storage.find(id);
    }

    @Override
    public List<User> getUsers() {
        return this.storage.getUsers();
    }
}
