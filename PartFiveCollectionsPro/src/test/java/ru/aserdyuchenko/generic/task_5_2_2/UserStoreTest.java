package ru.aserdyuchenko.generic.task_5_2_2;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;

/**
 * @author anton.
 */
public class UserStoreTest {
    /**
     * @param store - new store.
     */
    private UserStore<User> store = new UserStore();
    /**
     * @param user - new user.
     */
    private User user = new User("15");

    /**
     * Add user.
     */
    @Test
    public void whenAddUser() {
        store.add(user);
        User result = store.get(0);

        assertThat(result.getId(), is("15"));
    }

    /**
     * Update user.
     */
    @Test
    public void whenUpdateUser() {
        User userNew = new User("522");

        store.add(user);
        store.update(0, userNew);
        User result = store.get(0);

        assertThat(result.getId(), is("522"));
    }

    /**
     * Delete user.
     */
    @Test
    public void whenDeleteUser() {
        store.add(user);
        store.delete(0);

        assertNull(store.get(0));
    }
}