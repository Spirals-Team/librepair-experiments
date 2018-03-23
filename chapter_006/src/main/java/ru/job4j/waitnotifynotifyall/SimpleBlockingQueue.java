package ru.job4j.waitnotifynotifyall;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("lock")
    private Queue<T> queue = new LinkedList<>();
    private int size = 0;
    private final Object lock = new Object();

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public Queue<T> getQueue() {
        synchronized (lock) {
            return new LinkedList<>(queue);
        }
    }

    public void offer(T value) {
        synchronized (lock) {
            while (queue.size() >= size) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            size++;
            queue.add(value);
            lock.notifyAll();
        }
    }

    public T peek() {
        synchronized (lock) {
            while (queue.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T element = queue.poll();
            size--;
            lock.notifyAll();
            return element;
        }
    }
}
