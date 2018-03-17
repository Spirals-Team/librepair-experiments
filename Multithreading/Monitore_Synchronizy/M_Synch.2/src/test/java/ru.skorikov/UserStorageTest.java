package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 09.01.18
 * @ version: java_kurs_standart
 */
public class UserStorageTest {
    /**
     * Добавим пользователя.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenAddNewUserThenGetUser() throws Exception {
        UserStorage userStorage = new UserStorage();
        User user = new User(1, 100);
        userStorage.add(user);

        Assert.assertThat(userStorage.getUser(1), is(user));
    }

    /**
     * Добавим null.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenAddNullThenFalse() throws Exception {
        UserStorage userStorage = new UserStorage();

        Assert.assertFalse(userStorage.add(null));
    }

    /**
     * Обновим пользователя.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenUpdateUserThenGetUpdateUser() throws Exception {
        UserStorage userStorage = new UserStorage();
        User user = new User(1, 100);
        User user1 = new User(1, 300);
        userStorage.add(user);
        userStorage.update(user1);

        Assert.assertThat(userStorage.getUser(1), is(user1));
    }

    /**
     * Обновим null.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenUpdateNullThenFalse() throws Exception {
        UserStorage userStorage = new UserStorage();

        Assert.assertFalse(userStorage.update(null));
    }

    /**
     * Удалим пользователя.
     *
     * @throws Exception исключение.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void whenDeleteUserThenReturnNull() throws Exception {
        UserStorage userStorage = new UserStorage();
        User user = new User(1, 100);
        userStorage.add(user);
        userStorage.delete(user);

        userStorage.getUser(1);
    }

    /**
     * Удалим null пользователя.
     *
     * @throws Exception исключение.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void whenDeleteNullThenFalse() throws Exception {
        UserStorage userStorage = new UserStorage();

        userStorage.delete(new User(1));
    }

    /**
     * Переместим средства одного пользователя другому.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenTransferThwoUsersThenReturnChangeAmount() throws Exception {
        UserStorage userStorage = new UserStorage();
        User user = new User(1, 100);
        User user1 = new User(2, 200);
        userStorage.add(user);
        userStorage.add(user1);
        userStorage.transfer(1, 2, 50);

        Assert.assertEquals(userStorage.getUser(1).getAmount(), 50);
        Assert.assertEquals(userStorage.getUser(2).getAmount(), 250);
    }

    /**
     * Пробуем обновить несуществующего пользователя..
     *
     * @throws Exception исключение.
     */
    @Test(expected = NoSuchElementException.class)
    public void whenUpdateNullUserThenException() throws Exception {
        UserStorage userStorage = new UserStorage();
        User user = new User(1, 100);
        User user1 = new User(2, 200);
        userStorage.add(user);
        userStorage.update(user1);
    }

    /**
     * Пробуем удалить несуществующего пользователя.
     *
     * @throws Exception исключение.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void whenDeleteNullUserThenException() throws Exception {
        UserStorage userStorage = new UserStorage();
        User user = new User(1, 100);
        User user1 = new User(2, 200);
        userStorage.add(user);
        userStorage.delete(user1);
    }

    /**
     * Пробуем сделать перевод несуществующему пользователю.
     *
     * @throws Exception исключение.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void whenTransferNullUserThenException() throws Exception {
        UserStorage userStorage = new UserStorage();
        User user = new User(1, 100);
        User user1 = new User(2, 200);
        userStorage.add(user);
        userStorage.transfer(1, 2, 50);
    }

    /**
     * Пробуем сделать перевод между пользователями в три потока.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryTransferThreeThreads() throws Exception {
        UserStorage userStorage = new UserStorage();
        User user = new User(1, 100);
        User user1 = new User(2, 100);
        userStorage.add(user);
        userStorage.add(user1);
        // От первого второму 100
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                userStorage.transfer(1, 2, 100);
            }
        });
        //От второго первому 100
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                userStorage.transfer(2, 1, 100);
            }
        });
        //От первого второму 100.
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                userStorage.transfer(1, 2, 100);
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        Assert.assertThat(userStorage.getUser(1).getAmount(), is(0));
        Assert.assertThat(userStorage.getUser(2).getAmount(), is(200));
    }
}