package ru.job4j.waitnotifynotifyall;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {
    @Test
    public void whenProducerAddElementInQueueThenConsumerPeekElement() {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(5);
        Thread consumer = new Thread(() -> blockingQueue.peek());
        Thread producer = new Thread(() -> blockingQueue.offer(1));

        consumer.start();
        producer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(blockingQueue.getQueue().peek() == null, is(true));
    }
}