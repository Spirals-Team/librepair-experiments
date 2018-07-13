package ru.job4j.convert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserConvertTest {

    /**
     * Тест сравнивает имя пользователя с одним и тем же id в списке и мапе
     */
    @Test
    public void getUsersListConvertToHashMapAndFindSomeUser() {
        List<User> users = new ArrayList<>();
        HashMap<Integer, User> result = new HashMap<>();
        users.add(new User(45654,"Alex","London"));
        users.add(new User(65789,"Dima", "Madrid"));
        users.add(new User(88532, "Max","Tokyo"));
        result=UserConvert.Process(users);
        assertThat(result.get(65789).getName(), is("Dima"));
    }

}