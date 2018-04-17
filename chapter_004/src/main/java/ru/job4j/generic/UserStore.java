package ru.job4j.generic;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class UserStore extends AbstractStore<User> {
    public UserStore(int maxSize) {
        super(maxSize);
    }
}
