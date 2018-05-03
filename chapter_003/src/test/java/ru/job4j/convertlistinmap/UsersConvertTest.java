package ru.job4j.convertlistinmap;
/**
 * тестируем конвертирование ЛИст в мам
 */

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class UsersConvertTest {

    @Test
    public void process() {
        User user1 = new User("Саша",  30, "ChanyCity");
        User user2 = new User("Григорич", 34, "VengerovoCity");
        List<User> userlist = new ArrayList<>();
        userlist.addAll(Arrays.asList(user1, user2));
        HashMap<Integer, User> userExpected = new HashMap<>();
        HashMap<Integer, User> userHashMap = new HashMap<>();
        userExpected.put(user1.getId(), user1);
        userExpected.put(user2.getId(), user2);
        userHashMap = new UsersConvert().process(userlist);
        System.out.println(userExpected);
        assertThat(userExpected, Is.is(userHashMap));
    }
}