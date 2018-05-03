package ru.job4j.convertlistinmap;

import org.hamcrest.core.Is;
import org.junit.Test;
import ru.job4j.convertlistinmap.User;
import ru.job4j.convertlistinmap.UsersSort;

import java.util.*;

import static org.junit.Assert.*;

public class UsersSortTest {

    @Test
    public void whenSortthenUsersort() {
        User user1 = new User("Вася", 34, "Sib");
        User user2 = new User("Саша", 30, "ChanyCity");
        List<User> listUser = new ArrayList<>();
        System.out.println(listUser);

        listUser.addAll(Arrays.asList(user1, user2));
        System.out.println(listUser);
        Set<User> expected = new TreeSet<>(Arrays.asList(user2, user1));
        Set<User> result = new UsersSort().sort(listUser);
        System.out.println(result);
        assertThat(expected, Is.is(result));
    }

    @Test
    public void whenSortthensortNameLength() {
        User user1 = new User("Василий", 34, "Sib");
        User user2 = new User("Саша", 30, "ChanyCity");
        User user3 = new User("Сергей", 20, "Sib");
        User user4 = new User("Константин", 15, "ChanyCity");
        List<User> expcted = new ArrayList<>(Arrays.asList(user2, user3, user1, user4));
        List<User> listUser = new ArrayList<>();
        listUser.addAll(Arrays.asList(user2, user1, user3, user4));
        List<User> result = new UsersSort().sortNameLength(listUser);
        assertThat(expcted, Is.is(result));

    }

    @Test
    public void whenSortthensortByAllFields() {
        User user1 = new User("Сергей", 34, "Sib");
        User user2 = new User("Саша", 30, "ChanyCity");
        User user3 = new User("Сергей", 20, "Sib");
        User user4 = new User("Саша", 15, "ChanyCity");
        List<User> expcted = new ArrayList<>(Arrays.asList(user4, user2, user3, user1));
        List<User> listUser = new ArrayList<>();
        listUser.addAll(Arrays.asList(user1, user2, user3, user4));
        List<User> result = new UsersSort().sortByAllFields(listUser);
        assertThat(expcted, Is.is(result));
    }

}