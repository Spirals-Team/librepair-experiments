package ru.job4j.wait;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>();

    @Test
    public void whenTestQueueWhereStartTwoThread() {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                queue.offer("one");
            }
        });
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(String.format("Thread two peek: %s", queue.peek()));
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