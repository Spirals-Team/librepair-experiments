package ru.job4j.generic.store;

import ru.job4j.generic.base.User;

public class UserStore extends AbstractStore<User> {

    public UserStore(int size) {
        super(size);
    }
}
