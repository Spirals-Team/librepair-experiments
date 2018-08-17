package ru.job4j.sync;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Class for testing UserStorage
 */
public class UserStorageTest {
    /**
     * Method for testing
     * @throws InterruptedException exception
     */
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        UserStorage us = new UserStorage();

        User userA = new User(1, 500);
        User userB = new User(2, 1000);
        us.add(userA);
        us.add(userB);

        Thread thrA = new Thread(new Runnable() {
            @Override
            public void run() {
                us.transfer(userA.getId(), userB.getId(), 50);
            }
        });

        Thread thrB = new Thread(new Runnable() {
            @Override
            public void run() {
                us.transfer(userA.getId(), userB.getId(), 100);
            }
        });

        thrA.start();
        thrB.start();
        thrA.join();
        thrB.join();

        int result = userA.getAmount();
        assertThat(result, is(350));
    }
}
