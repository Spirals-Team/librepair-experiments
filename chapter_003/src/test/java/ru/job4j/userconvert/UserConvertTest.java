package ru.job4j.userconvert;

import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * UserConvertTest.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class UserConvertTest {
    /*** Input list of users, return HashMap of users.*/
    @Test
    public void whenListOfUserThenHashMapOfUser() {
        UserConvert userConvert = new UserConvert();
        User user1 = new User(1, "Ivan", "NY");
        User user2 = new User(2, "Alex", "NN");

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        HashMap<Integer, User> expect = new HashMap<>();
        expect.put(user1.getId(), user1);
        expect.put(user2.getId(), user2);

        assertThat(userConvert.process(list), is(expect));
    }
}
