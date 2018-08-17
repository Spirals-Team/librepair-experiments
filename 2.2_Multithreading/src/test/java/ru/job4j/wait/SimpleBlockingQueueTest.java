package ru.job4j.wait;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class SimpleBlockingQueueTest {

    private SimpleBlockingQueue<Integer> sbq;

    private Thread producer = new Thread() {
        @Override
        public void run() {

            try {
                for (int i = 0; i < 10; i++) {
                    sbq.offer(i);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Thread consumer = new Thread() {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 6; i++) {
                    sbq.peek();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Method for testing.
     * @throws InterruptedException exception
     */
    @Test
    public void whenAddTenPositionAndRemoveSixThenFirstElementIsSix() throws InterruptedException {
        sbq = new SimpleBlockingQueue();

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        int result = sbq.peek();
        assertThat(result, is(6));
    }

    /**
     * Method for testing.
     * @throws InterruptedException exception
     */
    @Test
    public void whenAddTenPositionAndRemoveSixThenSizeQueueIsFour() throws InterruptedException {
        sbq = new SimpleBlockingQueue();

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        int result = sbq.size();
        assertThat(result, is(4));
    }

}