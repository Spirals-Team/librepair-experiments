package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int lenght;

    public SimpleBlockingQueue(int lenght) {
        this.lenght = lenght;
    }

    private synchronized boolean isFull() {
        return lenght == queue.size();
    }

    public void offer(T value) {
        synchronized (this) {
            while (isFull()) {
                try {
                    wait();
                } catch (InterruptedException e) {
					System.out.println("Прерывание");
                }
            }
            System.out.println("Offer: " + value);
            queue.offer(value);
            notifyAll();
        }
    }

    public T poll() {
        synchronized (this) {
            while (queue.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            T result = queue.poll();
            System.out.println("Poll: " + result);
            notifyAll();
            return result;
        }
    }
}
