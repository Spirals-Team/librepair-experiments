package ru.aserdyuchenko.map.task_3;

import org.junit.Test;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anton.
 */
public class UserTest {

    @Test
    public void whenAddTwoUsersWithSameFieldsInMap() {
        Map<User, Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1993, 6, 16, 0, 0, 0);
        User first = new User("Ivan", 0, calendar);
        User second = new User("Ivan", 0, calendar);

        map.put(first, 1);
        map.put(second, 2);

        System.out.println(map);

    }

}