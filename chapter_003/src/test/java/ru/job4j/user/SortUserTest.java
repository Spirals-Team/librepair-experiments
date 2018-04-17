package ru.job4j.user;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SortUserTest {
    @Test
    public void sortByAge() {
        SortUser sort = new SortUser();
        List<User> user = new ArrayList<>();
        User user1 = new User("Yury", 27);
        User user2 = new User("Viktor", 25);
        User user3 = new User("Kiril", 19);
        User user4 = new User("Andrey", 17);
        user.add(user1);
        user.add(user2);
        user.add(user3);
        user.add(user4);
        Set<User> result = new TreeSet<>();
        result.add(user4);
        result.add(user3);
        result.add(user2);
        result.add(user1);
        assertThat(sort.sort(user), is(result));
    }

    @Test
    public void sortByLengthName() {
        SortUser sort = new SortUser();
        List<User> user = new ArrayList<>();
        User user1 = new User("Andrey", 27);
        User user2 = new User("Yury", 25);
        User user3 = new User("Kiril", 18);
        User user4 = new User("Kostia", 19);
        User user5 = new User("Pavel", 17);
        user.add(user1);
        user.add(user2);
        user.add(user3);
        user.add(user4);
        user.add(user5);
        assertThat(sort.sortNameLength(user), is(Arrays.asList(user2, user3, user5, user1, user4)));
    }

    @Test
    public void sortByAllFields() {
        SortUser sort = new SortUser();
        List<User> user = new ArrayList<>();
        User user1 = new User("A", 27);
        User user2 = new User("Bc", 25);
        User user3 = new User("Ba", 40);
        User user4 = new User("A", 19);
        User user5 = new User("C", 17);
        user.add(user1);
        user.add(user2);
        user.add(user3);
        user.add(user4);
        user.add(user5);
        assertThat(sort.sortByAllFields(user), is(Arrays.asList(user4, user1, user3, user2, user5)));
    }
}
