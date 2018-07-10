package ru.job4j.threads.lock;

import org.junit.Test;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class SimpleLockTest {

    @Test
    public void incrementCounterBySeveralThreads() {
        SimpleCounter counter = new SimpleCounter();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(counter);
            t.start();
        }
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}