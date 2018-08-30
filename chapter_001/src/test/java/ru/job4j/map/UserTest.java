package ru.job4j.map;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    @Test
    public void createTwoObjectsWithEqualFields() {
        Calendar birthday = new GregorianCalendar(2000, 01, 01);
        User user1 = new User("John", 5, birthday);
        User user2 = new User("John", 5, birthday);

        Map<User, String> userObjectMap = new HashMap<User, String>() {
            {
                put(user1, "user1");
                put(user2, "user2");
            }
        };

        System.out.println(userObjectMap.toString());
    }

    @Test
    public void objectsWithEqualFieldsHaveEqualHashCode() {
        Calendar birthday = new GregorianCalendar(2000, 01, 01);
        User user1 = new User("John", 5, birthday);
        User user2 = new User("John", 5, birthday);

        assertTrue(user1.equals(user2));
        assertThat(user1.hashCode(), is(user2.hashCode()));
    }

    @Test
    public void objectsWithDifferentFieldsHaveDifferentHashCode() {
        Calendar birthday = new GregorianCalendar(2000, 01, 01);
        User user1 = new User("John", 5, birthday);
        User user2 = new User("Smith", 5, birthday);

        assertFalse(user1.equals(user2));
        assertThat(user1.hashCode(), not(user2.hashCode()));
    }
}