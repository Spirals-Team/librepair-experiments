package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue;
    private static final int MAX_SIZE = 5;

    /**
     * Constructor.
     */
    public SimpleBlockingQueue() {
        this.queue = new LinkedList<>();
    }

    /**
     * Method for add in queue.
     * @param value for adding
     * @throws InterruptedException exception
     */
    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == MAX_SIZE) {
            wait();
        }
        queue.add(value);
        notifyAll();
    }

    /**
     * Method for remove from queue.
     * @return value
     * @throws InterruptedException exception
     */
    public synchronized T peek() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        notifyAll();
        return result;
    }

    /**
     * Method size.
     * @return queue size
     */
    public synchronized int size() {
        return queue.size();

    }
}

