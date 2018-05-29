package ru.job4j.list;

import org.junit.Test;
import ru.job4j.user.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SortUserTest {
    @Test
    public void whenListThenSet() {
        SortUser user = new SortUser();
        List<User> users = new ArrayList<>();
        Set<User> expectSet = new TreeSet<>();
        Set<User> actual;
        User daniil = new User("Daniil", 24);
        User ivan = new User("Ivan", 30);
        User anton = new User("Anton", 20);
        User ban = new User("Ban", 18);
        users.add(daniil);
        users.add(ivan);
        users.add(anton);
        users.add(ban);
        expectSet.addAll(users);
        actual = user.sort(users);
        assertThat(actual, is(expectSet));
    }
    @Test
    public void whenListThenSortByLength() {
        SortUser user = new SortUser();
        List<User> users = new ArrayList<>();
        List<User> actual;
        User daniil = new User("Daniil", 24);
        User ivan = new User("Ivan", 30);
        User anton = new User("Anton", 20);
        User ban = new User("Ban", 18);
        users.add(daniil);
        users.add(ivan);
        users.add(anton);
        users.add(ban);
        List<User> expectList = new ArrayList<>();
        expectList.add(ban);
        expectList.add(ivan);
        expectList.add(anton);
        expectList.add(daniil);
        actual = user.sortNameLength(users);
        assertThat(actual, is(expectList));
    }

    @Test
    public void whenListThenSortByAllFields() {
        SortUser user = new SortUser();
        List<User> users = new ArrayList<>();
        List<User> actual;
        User daniil = new User("Daniil", 24);
        User ivan = new User("Ivan", 18);
        User ivan23 = new User("Ivan", 23);
        User anton = new User("Anton", 20);
        User ban = new User("Ban", 18);
        User sergei = new User("Sergei", 21);
        users.add(daniil);
        users.add(ivan);
        users.add(ivan23);
        users.add(anton);
        users.add(sergei);
        users.add(ban);
        List<User> expectList = new ArrayList<>();
        expectList.add(anton);
        expectList.add(ban);
        expectList.add(daniil);
        expectList.add(ivan);
        expectList.add(ivan23);
        expectList.add(sergei);
        actual = user.sortByAllFields(users);
        assertThat(actual, is(expectList));
    }

}