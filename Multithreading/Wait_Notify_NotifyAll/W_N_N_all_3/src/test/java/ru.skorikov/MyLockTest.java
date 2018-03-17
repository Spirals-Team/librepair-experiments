package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 14.02.18
 * @ version: java_kurs_standart
 */
public class MyLockTest {
    /**
     * Тестовая переменная.
     */
    private int count = 0;

    /**
     * Проверим работу блокировки.
     * Запустим 20 потоков.
     * Но блокировкой владеет только один.
     * тестовая переменная равна 1.
     */
    @Test
    public void whenOneLockThenRezultOne() {
        MyLock myLock = new MyLock();

        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    myLock.lock();
                    count++;
                }
            }).start();
        }
        Assert.assertEquals(count, 1);
    }

    /**
     * Проверим работу блокировки.
     * Запустим 20 потоков.
     * Блокировка по очереди.
     * тестовая переменная равна 20.
     */
    @Test
    public void whenTwentyLockThenRezultThenty() {
        MyLock myLock = new MyLock();

        for (int i = 0; i <= 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    myLock.lock();
                    count++;
                    myLock.unlock();
                }
            }).start();
        }
        Assert.assertEquals(count, 20);
    }
}