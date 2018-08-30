package ru.job4j.waitnotify;

import static java.lang.Thread.currentThread;

/**
 * Producer.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */

public class Producer implements Runnable {

    private final SimpleBlockingQueue<Integer> queue;

    public Producer(final SimpleBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.printf("%s: start%n", currentThread().getName());
        for (int i = 0; i < 10; i++) {
            Integer value = i;
            System.out.printf("%s offer the value: %d%n",
                currentThread().getName(), value);
            queue.offer(value);
        }
    }
}
