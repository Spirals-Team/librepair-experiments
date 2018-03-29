package ru.job4j.monitore;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class UserStorageTest {
    @Test
    public void firstTest() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        storage.add(user1);
        storage.add(user2);
        storage.transfer(1, 2, 50);
        assertThat(user1.getAmount(), is(50));
        assertThat(user2.getAmount(), is(250));
    }
}