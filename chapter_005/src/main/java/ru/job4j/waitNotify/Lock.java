package ru.job4j.waitNotify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Lock {
    @GuardedBy("this")
    private boolean locked = false;
    private Thread owner;

    public synchronized void lock(Thread currentThread) throws InterruptedException {
        while (locked) {
            wait();
            owner = currentThread;
        }
        locked = true;
    }

    public synchronized void unlock(Thread currentThread) {
        if (owner.equals(currentThread)) {
            locked = false;
        }
        notifyAll();
    }
}
