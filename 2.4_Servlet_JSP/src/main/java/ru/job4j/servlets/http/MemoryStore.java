package ru.job4j.servlets.http;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class MemoryStore. Singleton.
 * @author ifedorenko
 * @since 29.07.2018
 */
public class MemoryStore implements Store {
    /**
     * Singleton variable
     */
    private static final MemoryStore INSTANCE = new MemoryStore();
    /**
     * Map for saving Users
     */
    private final ConcurrentHashMap<String, User> store = new ConcurrentHashMap<>();

    /**
     * add user in store.
     * @param user
     */
    @Override
    public void add(User user) {
        store.put(user.getLogin(), user);
    }

    /**
     * update user from store.
     * @param login use for find user for deleting
     * @param name name for updating
     * @param email name for updating
     */
    @Override
    public void update(String  login, String name, String email, String role, String password) {
        User temp = store.get(login);
        if (name != null) {
            temp.setName(name);
        }
        if (email != null) {
            temp.setEmail(email);
        }
    }

    /**
     * delete user from store.
     * @param id use for find user for deleting
     */
    @Override
    public void delete(int id) {
        store.remove(id);
    }

    /**
     * find all user in store.
     * @return list with all user
     */
    @Override
    public List<User> findAll() {
        return new ArrayList<User>(this.store.values());
    }

    /**
     * find user using id.
     * @param id id for finding.
     * @return User
     */
    @Override
    public User findById(int id) {
        return store.get(id);
    }

    @Override
    public User findByLogin(String login) {
        return null;
    }

    /**
     * Get MemoryStore.
     * @return MemoryStore instance
     */
    public static MemoryStore getInstance() {
        return INSTANCE;
    }
}
