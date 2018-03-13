package ru.job4j.list;

import ru.job4j.generic.*;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LinkedContainerTest {

    @Test
    public void whenAddUserToLinkedContainerThenOneCanGetUserFromLinkedContainer() {

        LinkedContainer linkedContainer = new LinkedContainer();

        User user1 = new User("1234");
        User user2 = new User("2222");

        linkedContainer.add(user1);
        linkedContainer.add(user2);

        User result = (User) linkedContainer.get(1);

        User expected = user2;

        assertThat(result, is(expected));
    }

    @Test
    public void whenAddUserToLinkedContainerThenOneCanGetUserFromLinkedContainerByIterator() {

        LinkedContainer linkedContainer = new LinkedContainer();

        User user1 = new User("1234");
        User user2 = new User("2222");

        linkedContainer.add(user1);
        linkedContainer.add(user2);

        Iterator iterator = linkedContainer.iterator();

        iterator.next();

        User result = (User) iterator.next();

        User expected = user2;

        assertThat(result, is(expected));
    }
}