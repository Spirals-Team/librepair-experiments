package ru.job4j.threads;

import org.junit.Test;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleBlockingQueueTest {
    private SimpleBlockingQueue simpleBlockingQueue = new SimpleBlockingQueue();

    private class Consummer extends Thread {
        @Override
        public void run() {
            simpleBlockingQueue.poll();
        }
    }

    private class Producer extends Thread {
        @Override
        public void run() {
            simpleBlockingQueue.offer("13");
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
           //Создаем нити.
        Thread first = new Consummer();
        Thread second = new Producer();

        first.start();
        second.start();
        first.join();
        second.join();
    }
}