package ru.job4j.collections.map;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Тесты.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 15.10.17;
 * @version $Id$
 * @since 0.1
 */
public class UserTest {
    /**
     * Мап.
     */
    private Map<User, Object> map;

    /**
     * Инициализация хранилища.
     */
    @Before
    public void start() {
        map = new HashMap<>();
    }

    /**
     *метод печатает содержимое мап.
     */
    @Test
    public void whenAddedTwoUsersWithOutHashCodeAndEquals() {
        map.put(new User("Petrov", 2), new Object());
        map.put(new User("Petrov", 2), new Object());
        for (User m : map.keySet()) {
            System.out.println(m);

        }
    }
}