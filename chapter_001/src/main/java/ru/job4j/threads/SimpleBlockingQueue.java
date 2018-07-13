package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
/**
 * Класс реализует шаблон Производитель Продавец
 */
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) {
        if (queue.offer(value)) {
            this.notify();
        }
    }

    public synchronized T poll() {
        while (queue.size() == 0) {
            System.out.println("waiting...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queue.poll();
    }
}
