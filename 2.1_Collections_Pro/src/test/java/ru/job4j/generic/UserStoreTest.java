package ru.job4j.generic;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Class UserStoreTest.
 */
public class UserStoreTest {
    /**
     * Test add User.
     */
    @Test
    public void whenCreateContainerShouldReturnTheSameType() {
        AbstractStore us = new UserStore(new SimpleArray(5));
        us.add(new User("1"));
        User result = (User) us.getArray().get(0);
        assertThat(result.getId(), is("1"));
    }

    /**
     * Test update User.
     */
    @Test
    public void whenUpdateOldUserThenOldUserReplacNewUser() {
        AbstractStore us = new RoleStore(new SimpleArray(5));
        us.add(new User("1"));
        us.add(new User("2"));
        User user = new User("2");
        us.update(user);
        User result = (User) us.getArray().get(1);
        assertThat(result, is(user));
    }

    /**
     * Test delete User.
     */
    @Test
    public void whenDeleteUserThenNextUserReplaceDeleteUser() {
        AbstractStore us = new RoleStore(new SimpleArray(5));
        User user1 = new User("1");
        us.add(user1);
        User user2 = new User("2");
        us.add(user2);
        User user3 = new User("3");
        us.add(user3);
        us.delete(user2);
        User result = (User) us.getArray().get(1);
        assertThat(result.getId(), is("3"));
    }

}