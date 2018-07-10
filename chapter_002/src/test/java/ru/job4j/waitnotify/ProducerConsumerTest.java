package ru.job4j.waitnotify;

import org.junit.Test;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class ProducerConsumerTest {

    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        Thread producerT = new Thread(producer, "producer");
        Thread consumerT = new Thread(consumer, "consumer");
        producerT.start();
        consumerT.start();
        producerT.join();
        Thread.sleep(3000L);
        consumerT.interrupt();
    }
}