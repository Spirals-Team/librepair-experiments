package ru.job4j.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Count {
    @GuardedBy("this")
    private int value;

    /**
     * Method increment
     */
    public synchronized void increment() {
        this.value++;
    }

    /**
     * Method get
     * @return value
     */
    public synchronized int get() {
        return this.value;
    }
}
