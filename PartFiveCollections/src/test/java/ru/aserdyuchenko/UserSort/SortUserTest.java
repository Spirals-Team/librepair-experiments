package ru.aserdyuchenko.UserSort;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by anton on 30.04.17.
 */
public class SortUserTest {
    @Test
    public void whenSortInteger() {
        SortUser sortUser = new SortUser();
        List<User> list = new ArrayList<>();
        list.add(new User("Bilbo Baggins", 111));
        list.add(new User("Frodo Baggins", 33));
        list.add(new User("Gandalf", 2000));
        list.add(new User("Gollum", 333));
        Set<User> result = sortUser.sort(list);
        assertThat(result.iterator().next().getName(), is("Frodo Baggins"));
    }

    @Test
    public void whenSortByHashCode() {
        SortUser sortUser = new SortUser();
        List<User> list = Arrays.asList(
                new User("Bilbo Baggins", 111),
                new User("Frodo Baggins", 33),
                new User("Gandalf", 2000),
                new User("Gollum", 333)
        );
        List<User> result = sortUser.sortHash(list);
        assertThat(result.toString(), is("[Bilbo Baggins, Gollum, Frodo Baggins, Gandalf]"));
    }

    @Test
    public void whenSortByLength() {
        SortUser sortUser = new SortUser();
        List<User> list = new ArrayList<>();
        list.add(new User("Bilbo Baggins", 111));
        list.add(new User("Gandalf", 2000));
        list.add(new User("Frodo Baggins", 33));
        list.add(new User("Gollum", 333));
        List<User> result = sortUser.sortLength(list);
        assertThat(result.toString(), is("[Gollum, Gandalf, Bilbo Baggins, Frodo Baggins]"));
    }
}