package ru.job4j.ioc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Component
public class UserAnnotationStorage implements Storage {
    private final Storage storage;

    @Autowired
    public UserAnnotationStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void add(User user) {
        this.storage.add(user);
    }
}
