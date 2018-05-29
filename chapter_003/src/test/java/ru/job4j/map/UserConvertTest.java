package ru.job4j.map;

import org.junit.Test;
import ru.job4j.user.User;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserConvertTest {
    @Test
    public void whenMapThenList() {
        UserConvert convert = new UserConvert();
        List<User> users = new ArrayList<>();
        User daniil = new User(1, "Daniil", "Orenburg");
        User ivan = new User(2, "Ivan", "Omsk");
        users.add(daniil);
        users.add(ivan);
        HashMap<Integer, User> expect = new HashMap<>();
        expect.put(daniil.getId(), daniil);
        expect.put(ivan.getId(), ivan);
        assertThat(convert.process(users).toString(), is(expect.toString()));

    }




}