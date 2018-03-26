package ru.job4j.collections.iterator;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.*;

public class UserSortTest {
    @Test
    public void userSortTest() {
        List<User> list = new ArrayList<>();
        list.addAll(Arrays.asList(
                new User("C", 3),
                new User("A", 4),
                new User("E", 1),
                new User("D", 2),
                new User("B", 5)
        ));

        Set<User> result = new SortUser().sort(list);
        Set<User> expected = new TreeSet<>();
        expected.addAll(Arrays.asList(
                new User("D", 2),
                new User("E", 1),
                new User("C", 3),
                new User("A", 4),
                new User("B", 5)
        ));
        assertThat(result, is(expected));
    }

    @Test
    public void sortNameLengthTest() {
        List<User> result = new ArrayList<>();
        result.addAll(Arrays.asList(
                new User("Сергей", 25),
                new User("Алексей", 30),
                new User("Сергей", 20),
                new User("Иван", 25)
        ));
        result = new SortUser().sortNameLength(result);

        List<User> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(
                new User("Иван", 25),
                new User("Сергей", 20),
                new User("Сергей", 25),
                new User("Алесей", 30)

        ));
        assertThat(result, is(expected));
    }

    @Test
    public void sortByAllFields() {
        List<User> result = new ArrayList<>();
        result.addAll(Arrays.asList(
                new User("Сергей", 25),
                new User("Иван", 30),
                new User("Сергей", 20),
                new User("Иван", 25)
        ));
        result = new SortUser().sortByAllFields(result);

        List<User> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(
                new User("Иван", 25),
                new User("Иван", 30),
                new User("Сергей", 20),
                new User("Сергей", 25)

        ));
        assertThat(result, is(expected));
    }
}
