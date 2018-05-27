package ru.job4j.generic;

import org.junit.Test;

import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStoreTest {
    @Test
    public void whenAddUser() {
        UserStore userStore = new UserStore();
        userStore.add(new User("Mike"));
        userStore.add(new User("Ivan"));
        userStore.add(new User("Alex"));
        assertThat(userStore.list.size(), is(3));
        assertThat(userStore.list.get(0), is(new User("Mike")));
        assertThat(userStore.list.get(1), is(new User("Ivan")));
        assertThat(userStore.list.get(2), is(new User("Alex")));

    }
    @Test
    public void whenDeleteUser() {
        UserStore userStore = new UserStore();
        userStore.add(new User("Mike"));
        userStore.add(new User("Ivan"));
        userStore.add(new User("Alex"));
        userStore.delete("Ivan");
        assertThat(userStore.list.size(), is(2));
        assertThat(userStore.list.get(1), is(new User("Alex")));
    }
    @Test
    public void whenReplaceUser() {
        UserStore userStore = new UserStore();
        userStore.add(new User("Mike"));
        userStore.add(new User("Ivan"));
        userStore.add(new User("Alex"));
        userStore.replace("Ivan", new User("Fedor"));
        assertThat(userStore.list.get(1), is(new User("Fedor")));
    }
    @Test
    public void whenFindByIdUser() {
        UserStore userStore = new UserStore();
        userStore.add(new User("Mike"));
        userStore.add(new User("Ivan"));
        userStore.add(new User("Alex"));
        assertThat(userStore.findById("Alex"), is(new User("Alex")));
    }

}