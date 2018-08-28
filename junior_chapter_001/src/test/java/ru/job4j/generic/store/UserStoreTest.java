package ru.job4j.generic.store;

import org.junit.Before;
import org.junit.Test;

import ru.job4j.generic.base.User;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStoreTest {
    private UserStore us;
    private User userOne = new User("1");
    private User userTwo = new User("2");
    private User userThree = new User("3");

    @Before
    public void setUp() {
        us = new UserStore(10);
    }

    @Test
    public void whenAddThreeUsersAndDeleteSecond() {
        us.add(userOne);
        us.add(userTwo);
        us.add(userThree);
        assertThat(us.findById("0"), is(userOne));
        assertThat(us.findById("1"), is(userTwo));
        assertThat(us.findById("2"), is(userThree));
        assertThat(us.delete("1"), is(true));
        assertThat(us.findById("0"), is(userOne));
        assertThat(us.findById("1"), is(userThree));
    }

    @Test
    public void whenDeleteNullElements() {
        assertThat(us.delete("0"), is(false));
    }

    @Test
    public void replaceUsers() {
        us.add(userOne);
        assertThat(us.findById("0"), is(userOne));
        us.replace("0", userTwo);
        assertThat(us.findById("0"), is(userTwo));
    }
}