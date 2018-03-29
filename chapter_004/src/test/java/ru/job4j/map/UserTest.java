package ru.job4j.map;

import org.junit.Test;

import java.util.HashMap;

public class UserTest {
    @Test
    public void getName() {
        User first = new User("John", 3);
        User second = new User("John", 3);
        HashMap<User, String> map = new HashMap<>();
        map.put(first, "first");
        map.put(second, "second");
        System.out.println(map);
    }
}