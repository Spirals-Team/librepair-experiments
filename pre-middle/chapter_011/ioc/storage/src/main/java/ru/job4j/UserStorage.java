package ru.job4j;

import ru.job4j.model.User;
import ru.job4j.storage.Storage;

import java.util.Collection;

/**
 * Class UserStorage.
 */
public class UserStorage {

    final Storage storage;

    public UserStorage(final Storage storage) {
        this.storage = storage;
    }

    public User findOne(Long key) {
        return (User) this.storage.findOne(key);
    }

    public Collection<User> findAll() {
        return (Collection<User>) this.storage.findAll();
    }

    public Long create(User entity) {
        return (Long) this.storage.create(entity);
    }

    public void update(User entity) {
        this.storage.update(entity);
    }

    public void delete(User entity) {
        this.storage.delete(entity);
    }

    public void deleteById(Long key) {
        this.storage.deleteById(key);
    }
}

