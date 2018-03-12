package ru.job4j.map;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserTest {
    private User userOne;
    private User userTwo;
    private Calendar calendar = Calendar.getInstance();
    @Before
    public void setUp() {
        userOne = new User("Владимир", 0, calendar);
        userTwo = new User("Владимир", 0, calendar);
    }

    @Test
    public void testAddingToTheMapTheSameUsers() throws Exception {
        Map<User, Object> map = new HashMap<>();
        map.put(userOne, "User one");
        map.put(userTwo, "User two");
        System.out.println(map);
    }
}