package ru.job4j.sort;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SortUserTest {

    @Test
    public void whenSortUserInListAndConvertToTreeSetThenGetTreeSetWithSortedUsers() {

        List<User> userList = new ArrayList<User>();
        userList.addAll(Arrays.asList(
                new User("serg", 33),
                new User("zuma", 11),
                new User("ivan", 22)

                )
        );

        TreeSet<User> resultUserTreeSet = new SortUser().sort(userList);

        TreeSet<User> expectedtuserTreeSet = new TreeSet<>();

        expectedtuserTreeSet.add(new User("zuma", 11));
        expectedtuserTreeSet.add(new User("ivan", 22));
        expectedtuserTreeSet.add(new User("serg", 33));

        assertThat(resultUserTreeSet, is(expectedtuserTreeSet));
    }

    @Test
    public void whenUserListSortedByNameAndAgeThenGetUserListSortedByNameAndAge() {

        List<User> userList = new ArrayList<User>();
        userList.addAll(Arrays.asList(
                new User("Сергей", 25),
                new User("Иван", 30),
                new User("Сергей", 20),
                new User("Иван", 25)
        ));

        SortUser sortUser = new SortUser();

        List<User> resultUserList = sortUser.sortByAllFields(userList);

        List<User> expectedUserList = new ArrayList<User>();
        expectedUserList.addAll(Arrays.asList(
                new User("Иван", 25),
                new User("Иван", 30),
                new User("Сергей", 20),
                new User("Сергей", 25)
        ));

        assertThat(resultUserList, is(expectedUserList));

    }

    @Test
    public void whenUserListSortedByNameThenGetUserListSortedByName() {

        List<User> userList = new ArrayList<User>();
        userList.addAll(Arrays.asList(
                new User("Сергей", 25),
                new User("Иван", 30),
                new User("Сергей", 20),
                new User("Иван", 25)
        ));

        SortUser sortUser = new SortUser();

        List<User> resultUserList = sortUser.sortNameLength(userList);

        List<User> expectedUserList = new ArrayList<User>();
        expectedUserList.addAll(Arrays.asList(
                new User("Иван", 30),
                new User("Иван", 25),
                new User("Сергей", 25),
                new User("Сергей", 20)
        ));

        assertThat(resultUserList, is(expectedUserList));

    }
}