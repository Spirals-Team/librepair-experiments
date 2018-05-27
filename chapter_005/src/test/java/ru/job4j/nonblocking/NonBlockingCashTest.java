package ru.job4j.nonblocking;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class NonBlockingCashTest {
    @Test
    public void whenTestNonClockingCash() throws OplimisticException {
        NonBlockingCash nonBlockingCash = new NonBlockingCash();
        nonBlockingCash.add(0, new NonBlockingCash.User("Test", "Test"));
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        nonBlockingCash.update(0, new NonBlockingCash.User(
                                "Test", String.format("data%d", i)));
                    } catch (OplimisticException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        nonBlockingCash.update(0, new NonBlockingCash.User(
                                String.format("name%d", i), "data"));
                    } catch (OplimisticException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadOne.start();
        threadTwo.start();
        try {
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}