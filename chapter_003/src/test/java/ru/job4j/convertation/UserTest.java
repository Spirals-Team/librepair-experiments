package ru.job4j.convertation;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserTest {
    @Test
    public void UserTest() {
        User user1 = new User(1, "first", "A");
        User user2 = new User(2, "second", "B");
        List<User> list = Arrays.asList(user1, user2);
        UserConvert convert = new UserConvert();
        HashMap<Integer, User> result = convert.process(list);
        HashMap<Integer, User> expected = new HashMap<>();
        expected.put(1, user1);
        expected.put(2, user2);
        assertThat(result, is(expected));
    }
}
