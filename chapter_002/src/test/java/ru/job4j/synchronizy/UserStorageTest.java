package ru.job4j.synchronizy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class UserStorageTest {

    private UserStorage storage;
    private User u1;
    private User u2;

    @Before
    public void setUp() {
        storage = new UserStorage();
        u1 = new User(1, 20);
        u2 = new User(2, 40);
    }

    @Test
    public void addRemoveUpdateUsers() {
        assertTrue(storage.add(u1));
        assertFalse(storage.add(u1));
        assertTrue(storage.add(u2));
        assertTrue(storage.delete(u1));
        assertFalse(storage.delete(u1));
        assertFalse(storage.update(new User(1, 50)));
        assertTrue(storage.update(new User(2, 50)));
    }

    @Test
    public void makeTransfer() {
        storage.add(u1);
        storage.add(u2);
        storage.transfer(1, 2, 10);
        assertThat(u1.getAmount(), is(10));
        assertThat(u2.getAmount(), is(50));
    }

    @Test
    public void makeTransfersWithThreads() throws InterruptedException {
        u1.setAmount(1000000);
        u2.setAmount(0);
        storage.add(u1);
        storage.add(u2);
        for (int i = 0; i < 200; i++) {
            Thread t = new Thread(() -> {
                System.out.printf(
                    "start: %s%n", Thread.currentThread().getName());
                for (int j = 0; j < 1000; j++) {
                    storage.transfer(u1.getId(), u2.getId(), 50);
                }
                System.out.printf(
                    "finish: %s%n", Thread.currentThread().getName());

            });
            t.start();
            t.join();
        }
        assertThat(u1.getAmount(), is(0));
        assertThat(u2.getAmount(), is(1000000));
    }
}
