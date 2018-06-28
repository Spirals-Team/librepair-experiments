package ru.job4j.search;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap; 

public class UserConvertTest {
    @Test
    public void whenUserConvertToMap() {
		User user = new User();
		user.setId(1);
		user.setName("Alex");
		user.setCity("Ekaterinburg");
		List<User> users = new ArrayList<>();
		users.add(user);
		HashMap<Integer, User> mapUser = new HashMap<>();
		mapUser.put(1, user);
        assertThat(new UserConvert().process(users), is(mapUser));
    }
}	