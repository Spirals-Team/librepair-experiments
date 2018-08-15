package ru.job4j.controltask.extra;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Switcher {
    @GuardedBy("this")
    private final StringBuilder sb;
    private volatile int currentState;

    /**
     * Constructor.
     */
    public Switcher() {
        sb = new StringBuilder("");
        currentState = 0;
    }

    /**
     * Method add.
     * @param value value for adding
     * @param state Thread state for work
     * @param nextState state for working next Thread
     * @throws InterruptedException exception
     */
    public synchronized void add(int value, int state, int nextState) throws InterruptedException {
        while (state != currentState) {
            wait();
        }
        for (int i = 0; i < 10; i++) {
            sb.append(value);
        }
        currentState = nextState;
        notifyAll();
    }

    /**
     * Method getStr.
     * @return string
     */
    public synchronized String getStr() {
        return sb.toString();
    }

    /**
     * Method length.
     * @return string length
     */
    public synchronized int length() {
        return sb.length();
    }
}

