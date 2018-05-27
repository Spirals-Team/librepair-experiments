package ru.job4j.monitore;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStorageTest {
    @Test
    public void whenTestUserStorage() {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        storage.seeAll();
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Transfer 50 = " + storage.transfer(1, 2, 50));
            }
        });
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Transfer 100 = " + storage.transfer(1, 2, 100));

            }
        });
        threadOne.start();
        threadTwo.start();
        try {
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        storage.seeAll();
    }
}