package ru.job4j.convertlistinmap;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void testDefoltRelizComporable() {
        User user1 = new User("Вася", 34, "Sib");
        User user2 = new User("Саша", 30, "ChanyCity");
        List<User> listUser = new ArrayList<>();
        listUser.addAll(Arrays.asList(user1, user2));
        listUser.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.compareTo(o2);
            }
        });
        List<User> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(user2, user1));
        assertThat(expected, Is.is(listUser));
    }

}