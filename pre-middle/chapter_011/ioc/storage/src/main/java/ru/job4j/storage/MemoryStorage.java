package ru.job4j.storage;

import ru.job4j.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class MemoryStorage.
 * Implement storage in memory. Entity to save is {@link User}
 */
public class MemoryStorage implements Storage<User, Long> {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User findOne(final Long key) {
        return this.users.get(key);
    }

    @Override
    public Collection<User> findAll() {
        return new ArrayList<>(this.users.values());
    }

    @Override
    public Long create(final User entity) {
        Long key = entity.getId();
        this.users.put(key, entity);
        return key;
    }

    @Override
    public void update(final User entity) {
        this.users.replace(entity.getId(), entity);
    }

    @Override
    public void delete(final User entity) {
        this.deleteById(entity.getId());
    }

    @Override
    public void deleteById(final Long key) {
        this.users.remove(key);
    }
}
