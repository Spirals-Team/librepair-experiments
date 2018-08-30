package ru.job4j.threads.lock;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import static java.lang.Thread.currentThread;

/**
 * Simple Lock
 */
@ThreadSafe
public class SimpleLock implements Lock {

    @GuardedBy("this")
    private boolean isLocked = false;
    @GuardedBy("this")
    private Thread lockedBy = null;

    /**
     * Lock resource.
     *
     * @throws InterruptedException
     */
    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {
            Thread currentT = Thread.currentThread();
            while (this.isLocked && this.lockedBy != currentT) {
                wait();
            }
            this.isLocked = true;
            this.lockedBy = currentT;
        }
    }

    /**
     * Unlock resource
     */
    @Override
    public void unlock() {
        synchronized (this) {
            if (currentThread() == this.lockedBy) {
                this.isLocked = false;
                notifyAll();
                this.lockedBy = null;
            }
        }
    }
}
