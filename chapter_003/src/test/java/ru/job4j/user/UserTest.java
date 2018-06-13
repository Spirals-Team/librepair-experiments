package ru.job4j.user;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.*;

public class UserTest {
    @Test
    public void sortTest() {
        List<User> list = Arrays.asList(
                new User(63, "Bill Gates"),
                new User(34, "Mark Zuckerberg"),
                new User(47, "Elon Mask"));
        SortUser sortUser = new SortUser();
        Set<User> result = sortUser.sort(list);
        assertThat(result.iterator().next().getAge(), is(34));
    }

    @Test
    public void sortNameLengthTest() {
        String expect = "[Mask (47), Gates (63), Zuckerberg (34)]";
        List<User> list = Arrays.asList(
                new User(63, "Gates"),
                new User(34, "Zuckerberg"),
                new User(47, "Mask"));
        List<User> sort = new SortUser().sortNameLength(list);
        String result = sort.toString();
        assertThat(result, is(expect));
    }

    @Test
    public void sortByAllFieldsTest() {
        List<User> list = Arrays.asList(
                new User(34, "Сергей"),
                new User(18, "Вася"),
                new User(100, "Сергей"),
                new User(47, "Вася"));
        List<User> result = new SortUser().sortByAllFields(list);
        List<User> expect = Arrays.asList(
                new User(18, "Вася"),
                new User(47, "Вася"),
                new User(34, "Сергей"),
                new User(100, "Сергей"));
        assertThat(result, is(expect));
    }
}
