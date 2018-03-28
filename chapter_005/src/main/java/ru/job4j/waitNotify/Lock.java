package ru.job4j.waitNotify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Lock {
    @GuardedBy("this")
    private boolean locked = false;
    @GuardedBy("this")
    private Thread owner;

    public synchronized void lock() throws InterruptedException {
        while (locked) {
            wait();
        }
        locked = true;
        owner = Thread.currentThread();
    }

    public synchronized void unlock() {
        if (owner.equals(Thread.currentThread())) {
            locked = false;
            notifyAll();
            owner = null;
        }
    }
}
