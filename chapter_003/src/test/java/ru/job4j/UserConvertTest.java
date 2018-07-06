package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class UserConvertTest {
    @Test
    public void convertListIntoHashMap() {
        List<User> list = new ArrayList<>();
        User user1 = new User(1, "Yury", "Brest");
        User user2 = new User(2, "Kiril", "Minsk");
        User user3 = new User(3, "Igor", "Gomel");
        list.add(user1);
        list.add(user2);
        list.add(user3);
        UserConvert convert = new UserConvert();
        HashMap<Integer, User> map = convert.process(list);

        assertThat(map.get(1), is(user1));
        assertThat(map.get(2), is(user2));
        assertThat(map.get(3), is(user3));
    }
}
