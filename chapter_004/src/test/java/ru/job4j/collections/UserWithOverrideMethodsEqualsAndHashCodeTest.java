package ru.job4j.collections;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserWithOverrideMethodsEqualsAndHashCodeTest {

    /**
     * Будет выведен один объект юзер, т.к. сначала добавится в мапу первый юзер, а затем при добавлении
     * второго юзера произойдет перезапись первого юзера вторым, т.к. хэши у них одинаковые и метод equals выдает, что объекты
     * одинаковые. В итоге в мапе останется только второй юзер.
     */
    @Test
    public void whenCreateTwoUserWithOverrideEqualsAndHashCodeAndAddThenInMapThenAddTwoElement() {
        UserWithOverrideMethodsEqualsAndHashCode user1 = new UserWithOverrideMethodsEqualsAndHashCode("123");
        UserWithOverrideMethodsEqualsAndHashCode user2 = new UserWithOverrideMethodsEqualsAndHashCode("123");
        Map<User, Object> map = new HashMap<>();
        map.put(user1, "1");
        map.put(user2, "2");
        assertThat(map.size(), is(1));
    }
}