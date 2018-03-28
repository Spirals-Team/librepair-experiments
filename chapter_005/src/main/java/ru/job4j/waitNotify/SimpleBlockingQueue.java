package ru.job4j.waitNotify;

import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    final private Queue<T> queue = new LinkedList<>();
    private int capacity = 0;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (queue) {
            queue.notify();
            while (queue.size() == capacity) {
                queue.wait();
            }
            queue.add(value);
        }
    }

    public T peek() throws InterruptedException {
        synchronized (queue) {
            queue.notify();
            while (queue.isEmpty()) {
                queue.wait();
            }
            return queue.poll();
        }
    }

    public synchronized int size() {
        return queue.size();
    }
}
