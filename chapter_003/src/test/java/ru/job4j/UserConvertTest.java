package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserConvertTest {

    @Test
    public void whenConvertListToHashMap() {
        UserConvert userConvert = new UserConvert();
        List<User> list = new ArrayList<User>();
        User user1 = new User(78, "Ivan Dulin", "Luga");
        User user2 = new User(8, "Petr Sidorov", "Tula");
        list.add(user1);
        list.add(user2);
        HashMap<Integer, User> expect = new HashMap<Integer, User>();
        expect.put(user1.getId(), user1);
        expect.put(user2.getId(), user2);
        assertThat(userConvert.process(list), is(expect));
    }
}