package ru.job4j.wait;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class SimpleLockTest {
    private SimpleLock lock;


    /**
     * Method for testing work lock.
     * @throws InterruptedException exception
     */
    @Test
    public void whenOneThreadLockAMethodThenNextThreadGoToStateIsWaiting() throws InterruptedException {
        lock = new SimpleLock();
        Thread thrA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        Thread thrB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        thrA.start();
        thrA.join(100);
        thrB.start();
        thrB.join(1000);

        Thread.State result = thrB.getState();
        assertThat(result, is(Thread.State.WAITING));
    }

    /**
     * Method for testing work unlock.
     * @throws InterruptedException exception
     */
    @Test
    public void whenAllThreadsIsFinishedWorkThenLockStateIsFalse() throws InterruptedException {
        lock = new SimpleLock();

        Thread thrA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        Thread thrB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        thrA.start();
        thrB.start();
        thrA.join();
        thrB.join();

        boolean result = lock.getLockState();
        boolean expect = false;
        assertThat(result, is(expect));
    }


}