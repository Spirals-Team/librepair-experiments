package ru.job4j.collections;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserWithOverrideHashCodeTest {
    /**
     * Будут добавлены оба элемента в мапу, т.к. хэши теперь одинаковые у объектов юзер, но метод equals
     * все равно возвращает, что эти элементы разные, т.к. разные ссылки выдает new для них
     */
    @Test
    public void whenCreateTwoUserWithOverrideHashCodeAndAddThenInMapThenAddTwoElement() {
        UserWithOverrideHashCode user1 = new UserWithOverrideHashCode("123");
        UserWithOverrideHashCode user2 = new UserWithOverrideHashCode("123");
        Map<User, Object> map = new HashMap<>();
        map.put(user1, "1");
        map.put(user2, "2");
        assertThat(map.size(), is(2));
    }
}