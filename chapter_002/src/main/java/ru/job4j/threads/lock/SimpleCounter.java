package ru.job4j.threads.lock;

import static java.lang.Thread.currentThread;

/**
 * Simple counter
 */
public class SimpleCounter implements Runnable {

    private Lock lock = new SimpleLock();
    private int counter = 0;

    public void increment() throws InterruptedException {
        lock.lock();
        for (int i = 0; i < 5; i++) {
            System.out.printf("%s: %d%n", currentThread().getName(), counter++);
        }
        lock.unlock();
    }

    @Override
    public void run() {
        try {
            increment();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.yield();
    }
}
