package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class UserStoreTest {

    private UserStore userStore;

    @Before
    public void getUp() {
        userStore = new UserStore(5);
    }

    @Test
    public void whenAddUserThenUserStoreHasTheUser() {
        User user = new User("first");
        userStore.add(user);
        assertThat(userStore.findById("first"), is(user));
    }

    @Test
    public void whenReplaceUserWithTheIdThenUserStoreHasNewUserWithTheSameId() {
        userStore.add(new User("first"));
        User user = new User("second");
        userStore.replace("first", user);
        assertThat(userStore.findById("second"), is(user));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenDeleteUserWithTheIdThenUserStoreHasNewUserWithTheSameId() {
        User user = new User("first");
        userStore.add(user);
        assertThat(userStore.findById("first"), is(user));
        userStore.delete("first");
        userStore.findById("first");
    }
}