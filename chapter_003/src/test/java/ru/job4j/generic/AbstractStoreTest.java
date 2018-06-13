package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AbstractStoreTest {
    private SimpleArray<User> list;
    private UserStore us;

    @Before
    public void beforeTest() {
        list = new SimpleArray<>(5);
        us = new UserStore(list);
        us.add(new User("One"));
        us.add(new User("Two"));
        us.add(new User("Three"));
    }

    @Test
    public void add() {
        User fourth = new User("fourth");
        us.add(fourth);
        assertThat(list.get(3), is(fourth));
    }

    @Test
    public void replace() {
        User fifth = new User("Fifth");
        us.replace("One", fifth);
        assertThat(list.get(0), is(fifth));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void delete() {
        us.delete("Tw0");
        list.get(1);
    }

    @Test
    public void findById() {
        assertThat(us.findById("Three"), is(list.get(2)));
    }

    @Test
    public void getIndex() {
        assertThat(us.getIndex("Two"), is(1));
    }
}