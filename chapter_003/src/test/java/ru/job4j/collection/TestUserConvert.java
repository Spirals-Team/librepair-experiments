package ru.job4j.collection;

import org.junit.Test;
import ru.job4j.collection.User;
import ru.job4j.collection.UserConvert;

import static org.hamcrest.core.Is.is;

import java.util.*;

import static org.junit.Assert.assertThat;

/**
 * Тестовый класс для тестирования UserConvert.
 */
public class TestUserConvert {
    /**
     * Тестовый метод для UserConvert.
     */
    @Test
    public void whenConvertListThenGetMap() {
        List<User> users = new ArrayList<>();
        User user1 = new User(1, "Петя", "Брянск");
        User user2 = new User(2, "Вова", "Ставрополь");
        users.add(user1);
        users.add(user2);
        HashMap<Integer, User> resault = new HashMap<>();
        resault.put(1, user1);
        resault.put(2, user2);
        assertThat(new UserConvert().process(users), is(resault));
    }

    /**
     * Тестовый метод для UserConvert. С null.
     */
    @Test
    public void whenConvertListThenGetMapIfNull() {
        List<User> users = new ArrayList<>();
        User user1 = new User(1, "Петя", "Брянск");
        User user2 = null;
        users.add(user1);
        users.add(user2);
        HashMap<Integer, User> resault = new HashMap<>();
        resault.put(1, user1);
        assertThat(new UserConvert().process(users), is(resault));
    }
}
