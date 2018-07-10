package ru.job4j.waitnotify;

import static java.lang.Thread.currentThread;

/**
 * Consumer.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */

public class Consumer implements Runnable {

    private final SimpleBlockingQueue<Integer> queue;

    public Consumer(final SimpleBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.printf("%s: start%n", currentThread().getName());
        while (!currentThread().isInterrupted()) {
            Integer value = queue.peek();
            System.out.printf("%s peak the value: %d%n",
                currentThread().getName(), value);
        }
    }
}
