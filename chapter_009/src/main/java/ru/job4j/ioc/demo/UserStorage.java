package ru.job4j.ioc.demo;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStorage {

    private final Storage storage;

    public UserStorage(final Storage storage) {
        this.storage = storage;
    }

    public void add(User user) {
        this.storage.add(user);
    }
}
