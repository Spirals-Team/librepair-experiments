package ru.job4j.JMM;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SynchronizedCounter implements Runnable {
    @GuardedBy("this")
    private int count = 0;

    public synchronized void increment() {
        count = count + 1;
    }

    public synchronized int getCount() {
        return count;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            increment();
        }
    }
}
