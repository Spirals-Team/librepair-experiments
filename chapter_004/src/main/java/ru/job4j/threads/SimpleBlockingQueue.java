package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final Object lock = new Object();
    private int index =  0;

    public void offer(T value) {
        synchronized (lock) {
            if (index > queue.size()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                queue.offer(value);
                index++;
                this.lock.notify();
        }
    }

    public T poll() {
        synchronized (lock) {
            T result = null;

            if (index == 0) {
                try {
                    lock.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            result = queue.poll();
            index--;
            lock.notify();
            return result;
        }
    }
}
