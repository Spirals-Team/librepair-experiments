package ru.job4j.generic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserStoreTest {
    @Test
    public void whenAdd() {
        UserStore<User> store = new UserStore();
        User user = new User("one");

        store.add(user);

        assertThat(store.findById("one"), is(user));

    }

    @Test
    public void whenReplace() {
        UserStore<User> store = new UserStore();
        User user1 = new User("one");
        User user2 = new User("five");
        User user3 = new User("three");

        store.add(user1);
        store.add(user2);
        store.add(user3);
        store.replace(user2.getId(), new User("two"));

        assertThat(store.findById("two"), is(store.get(1)));
    }

    @Test
    public void whenDeleteSecondElement() {
        UserStore store = new UserStore();
        User user1 = new User("first");
        User user2 = new User("five");
        User user3 = new User("three");

        store.add(user1);
        store.add(user2);
        store.add(user3);
        store.delete("five");

        assertThat(store.findById("three"), is(store.get(1)));
    }

    @Test
    public void whenDelete() {
        UserStore store = new UserStore();
        User user1 = new User("first");
        User user2 = new User("two");

        store.add(user1);
        store.add(user2);

        assertThat(store.findById("two"), is(store.get(1)));
    }

}