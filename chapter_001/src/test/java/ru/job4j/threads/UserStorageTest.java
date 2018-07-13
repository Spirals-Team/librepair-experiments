package ru.job4j.threads;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Класс для тестирования UserStorage
 */
public class UserStorageTest {
    UserStorage stoge = new UserStorage();

    @Test
    /**
     * Тестируем основные методы тестируемого класса
     */
    public void testUserStorage() {
        User userA = new User(1,100);
        User userB = new User(2, 200);
        User userC = new User(3, 300);
        stoge.add(userA);
        stoge.add(userB);
        stoge.add(userC);
        assertThat(userB.getAmount(), is(200));
        stoge.update(new User(3,400));
        assertThat(userC.getAmount(), is(400));
        stoge.transfer(1, 2, 50);
        assertThat(userA.getAmount(), is(50));
        assertThat(userB.getAmount(), is(250));
    }

}