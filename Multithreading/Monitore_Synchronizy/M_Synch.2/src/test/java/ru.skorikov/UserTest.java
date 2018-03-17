package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 09.01.18
 * @ version: java_kurs_standart
 */
public class UserTest {
    /**
     * Проверим неравенство пользователей.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenNotEqualsUsersThenFalse() throws Exception {
        User user = new User(1, 100);
        User user1 = null;

        Assert.assertFalse(user.equals(user1));
    }

    /**
     * Пробуем получить хэшкод.
     * @throws Exception исключение.
     */
    @Test
    public void tryGetHashCode() throws Exception {
        User user = new User(1, 100);
        int hash = user.hashCode();

        Assert.assertThat(user.hashCode(), is(hash));
    }
    /**
     * Проверим неравенство пользователя и другого объекта..
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenNotEqualsUserThenFalse() throws Exception {
        User user = new User(1, 100);
        UserStorage userStorage = new UserStorage();

        Assert.assertFalse(user.equals(userStorage));
    }

}