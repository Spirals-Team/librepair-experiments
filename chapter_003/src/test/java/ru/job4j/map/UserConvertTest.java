package ru.job4j.map;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.*;

public class UserConvertTest {

    /**
     * Тест метода process.
     */
    @Test
    public void userConvertTest() {
        User userFirst = new User(1, "Elon", "Silicon Valley");
        User userSecond = new User(2, "Bill", "Silicon Valley");
        User userThird = new User(3, "Jobs", "Silicon Valley");


        List<User> listUser = new ArrayList<>();
        listUser.addAll(Arrays.asList(userFirst, userSecond, userThird));

        Map<Integer, User> map = new HashMap<>();
        map.put(userFirst.getId(), userFirst);
        map.put(userSecond.getId(), userSecond);
        map.put(userThird.getId(), userThird);

        UserConvert us = new UserConvert();

        assertThat(true, is(map.equals(us.process(listUser))));
    }
}
