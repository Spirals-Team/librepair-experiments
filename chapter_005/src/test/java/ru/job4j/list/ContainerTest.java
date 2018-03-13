package ru.job4j.list;


import ru.job4j.generic.*;
import java.util.Iterator;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ContainerTest {

    @Test
    public void whenAddUserToContainerThenOneCanGetUserFromTheContainer() {

        Container container = new Container();

        User user1 = new User("1234");
        User user2 = new User("2222");


        container.add(user1);
        container.add(user2);

        User result = (User) container.get(1);

        User expected = user2;

        assertThat(result, is(expected));

    }
}
