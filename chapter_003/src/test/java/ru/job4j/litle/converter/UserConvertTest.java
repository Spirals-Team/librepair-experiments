package ru.job4j.litle.converter;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *Test .
 * @author Hincu Andrei (andreih1981@gmail.com) by 17.09.17;
 * @version $Id$
 * @since 0.1
 */
public class UserConvertTest {
    /**
     * test.
     */
    @Test
    public void whenHasArrayListWithUsersThenConvertToHashMap() {
        UserConvert userConvert = new UserConvert();
        ArrayList<User> list = new ArrayList<>();
        list.add(new User(1, "Ivan", "Chisinau"));
        list.add(new User(2, "Alex", "Vorkuta"));
        list.add(new User(3, "Denis", "Magadan"));
        HashMap<Integer, User> mapResult = userConvert.process(list);
        HashMap<Integer, User> ex = new HashMap<>();
        ex.put(1, new User(1, "Ivan", "Chisinau"));
        ex.put(2, new User(2, "Alex", "Vorkuta"));
        ex.put(3, new User(3, "Denis", "Magadan"));
        assertThat(mapResult, is(ex));
    }
}
