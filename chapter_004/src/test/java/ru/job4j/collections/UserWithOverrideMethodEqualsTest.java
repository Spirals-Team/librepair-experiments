package ru.job4j.collections;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserWithOverrideMethodEqualsTest {

    /**
     * Будут добавлены оба элемента в мапу, т.к. объекты равны, но хэши у юзеров разные, т.к. исполуется
     * hashCode()  по умочанию, который в своей основе имеет генератор случаййных чисел.
     */
    @Test
    public void whenCreateTwoUserWithOverrideEqualsAndAddThenInMapThenAddTwoElement() {
        UserWithOverrideMethodEquals user1 = new UserWithOverrideMethodEquals("123");
        UserWithOverrideMethodEquals user2 = new UserWithOverrideMethodEquals("123");
        Map<User, Object> map = new HashMap<>();
        map.put(user1, "1");
        map.put(user2, "2");
        assertThat(map.size(), is(2));
    }
}