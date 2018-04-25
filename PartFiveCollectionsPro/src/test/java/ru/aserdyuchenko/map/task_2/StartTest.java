package ru.aserdyuchenko.map.task_2;

import org.junit.Test;
import ru.aserdyuchenko.map.task_1.User;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * @author anton.
 */
public class StartTest {

    @Test
    public void whenAddTwoUsersWithSameFieldsInMap() {
        User first = new User("Ivan", 0, Calendar.getInstance());
        User second = new User("Ivan", 0, Calendar.getInstance());

        Start start = new Start();
        start.add(first, 1);
        start.add(second, 2);

        start.showMap();
    }

}