package ru.job4j.monitor;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class UserStorageTest {

    private class ThreadAdd extends Thread {
        private final UserStorage storage;
        private User user;

        private ThreadAdd(final UserStorage storage, User user) {
            this.storage = storage;
            this.user = user;
        }

        @Override
        public void run() {
            storage.add(user);
        }
    }

    @Test
    public void whenAddNewUserThenStorageHasTheUser() {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 1000));
        storage.add(new User(1, 200));
        User user1 = new User(1, 1000);
        User user2 = new User(1, 200);
        Thread first = new ThreadAdd(storage, user1);
        Thread second = new ThreadAdd(storage, user2);
        first.start();
        second.start();
        assertThat(storage.getUser(1), is(user1));
    }

    @Test
    public void whenUpdateUserThenTheUserHasNewAmount() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 1000);
        User user2 = new User(1, 500);
        storage.add(user1);
        storage.update(user2);
        assertTrue(storage.getUser(1).equals(new User(1, 500)));
    }

    @Test
    public void whenDeleteUserThenTheUserDoesNotExistInStorage() {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 1000));
        storage.delete(new User(1, 1000));
        assertNull(storage.getUser(1));
    }

    @Test
    public void testTransfer() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        storage.add(user1);
        storage.add(user2);
        storage.transfer(1, 2, 50);
        assertFalse("There is not User with id = 3", storage.transfer(1, 3, 50));
        assertFalse("There is not User with id = 3", storage.transfer(3, 1, 50));
        assertFalse("Amount of user is not enough for transfer", storage.transfer(1, 2, 101));
        assertThat(user1.getAmount(), is(50));
        assertThat(user2.getAmount(), is(250));
    }
}