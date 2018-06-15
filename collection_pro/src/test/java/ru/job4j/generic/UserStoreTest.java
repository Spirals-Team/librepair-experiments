package ru.job4j.generic;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.core.Is.is;

public class UserStoreTest {
    @Test
    public void whenAddThatNewSimpleArrayElementUser() {
        UserStore store = new UserStore(2);
        User user = new User("1");
        User test = new User("2");
        store.add(user);
        store.add(test);
        assertThat(store.findVyId("2"), is(test));
        assertThat(store.delete("2"), is(true));
        assertThat(store.replace("2"), is(true));
        assertThat(store.replace("3"), is(false));
    }
}