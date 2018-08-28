package ru.job4j.monsyn;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {
    User one;
    User two;
    UserStorage us;

    @Before
    public void setUp() {
        one = new User(1, 100);
        two = new User(2, 200);
        us = new UserStorage();
    }

    @Test
    public void whenYouAddAndRemoveUser() {
        assertThat(us.add(one), is(true));
        assertThat(us.delete(one), is(true));
        assertThat(us.update(one), is(false));
    }

    @Test
    public void whenTransferMoney() {
        us.add(one);
        us.add(two);
        us.transfer(1, 2, 50);
        assertThat(one.getAmount(), is(50));
        assertThat(two.getAmount(), is(250));
    }
}