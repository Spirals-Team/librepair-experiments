package ru.job4j.waitnotify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.currentThread;

/**
 * Simple Blocking Queue.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    @GuardedBy("this")
    private int size;

    public SimpleBlockingQueue(final int size) {
        this.size = size;
    }

    /**
     * Put value to queue.
     *
     * @param value
     */
    public void offer(final T value) {
        synchronized (this) {
            while (isFull()) {
                try {
                    System.out.printf(
                        "%s queue is blocked.%n", currentThread().getName());
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(value);
            notifyAll();
        }
    }

    /**
     * Poll(peak and remove) value from queue.
     *
     * @return value
     */
    public T peek() {
        synchronized (this) {
            while (isEmpty()) {
                try {
                    System.out.printf(
                        "%s queue is blocked.%n", currentThread().getName());
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T value = queue.poll();
            notifyAll();
            return value;
        }
    }

    private boolean isEmpty() {
        synchronized (this) {
            return queue.size() < 1;
        }
    }

    private boolean isFull() {
        synchronized (this) {
            return queue.size() >= this.size;
        }
    }
}