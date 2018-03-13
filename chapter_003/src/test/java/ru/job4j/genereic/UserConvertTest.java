package ru.job4j.genereic;

import org.junit.Test;
import ru.job4j.generic.ConvertList;
import ru.job4j.generic.User;
import ru.job4j.generic.UserConvert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserConvertTest {

    @Test
    public void whenConvertFromListToMapThenGetMap()  {

        List<User> listOfUsers = new ArrayList<User>();
        listOfUsers.addAll(Arrays.asList(new User(1, "serg", "king"), new User(2, "ivan", "spb")));

        UserConvert userConvert = new UserConvert();

        HashMap<Integer, User> userHashMapresult = userConvert.process(listOfUsers);

        HashMap<Integer, User> userHashMapExpected = new HashMap<Integer, User>();

        userHashMapExpected.put(1, new User(1, "serg", "king"));
        userHashMapExpected.put(2, new User(2, "ivan", "spb"));

        assertThat(userHashMapresult, is(userHashMapExpected));

    }
}