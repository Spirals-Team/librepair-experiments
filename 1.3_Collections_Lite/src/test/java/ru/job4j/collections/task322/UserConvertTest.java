package ru.job4j.collections.task322;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;



/**
 * Class UserConvertTest.
 * @author Igor Fedorenko (mailto:if.zommy@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserConvertTest {
    /**
     * Test convert from List to Map.
     */
    @Test
    public void whenAddListOfUserThenReturnMapOfIntegerAndUser() {
        UserConvert uc = new UserConvert();
        List<User> list = new ArrayList<>();
        Map<Integer, User> exist = new HashMap<>();
        User user1 = new User(1, "Masha", "Moscow");
        User user2 = new User(2, "Pasha", "Moscow");
        User user3 = new User(3, "Dasha", "NYC");
        list.add(user1);
        list.add(user2);
        list.add(user3);
        exist.put(1, user1);
        exist.put(2, user2);
        exist.put(3, user3);
        Map<Integer, User> result = uc.process(list);
        assertThat(result, is(exist));
    }
}
