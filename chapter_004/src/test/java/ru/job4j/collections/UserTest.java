package ru.job4j.collections;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserTest {

    /**
     * Будут добавлены оба элемента в мапу, т.к. каждого юзера создаем через new User(). по умолчанию
     * hashCode() использует внутри нативную реализацию на основе генератора случайных чисел, отсюда получаем два разных хэша
     * и две разных ячейки в хэшМапе.
     */
    @Test
    public void whenCreateTwoUserAndAddThenInMapThenAddTwoElement() {
        User user1 = new User("123");
        User user2 = new User("123");
        Map<User, Object> map = new HashMap<>();
        map.put(user1, "1");
        map.put(user2, "2");
        assertThat(map.size(), is(2));
    }

}