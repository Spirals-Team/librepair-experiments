package ru.job4j.threads;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Класс для тестирования реализации Lock
 */
public class LockTest {
    private Lock lock = new Lock();

    /**
     * Метод реализует простую процедуру с lock
     */
    private void sample() {
        System.out.println(String.format("Thread %s enter..",Thread.currentThread().getName()));
        lock.lock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
        System.out.println(String.format("Thread %s exit..",Thread.currentThread().getName()));
    }

    /**
     * Метод реализует поток с тестовым методом
     * @return
     */
    private Thread moreThreads() {
        return new Thread() {
            @Override
            public void run() {
                sample();
            }
        };
    }

    @Test
    /**
     * Метод создает несколько потоков с тестовой процедурой и ждет некоторое время
     */
    public void testLock() {
        Thread temp;
        for( int i = 0; i < 5; i++) {
            temp = moreThreads();
            System.out.println(temp.getName() + " created");
            temp.start();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertThat(lock.isLock(), is(true));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(lock.isLock(), is(false));
    }

}