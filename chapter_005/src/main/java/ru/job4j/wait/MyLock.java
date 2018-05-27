package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class MyLock {
    @GuardedBy("lock")
    private final Object lock = new Object();
    @GuardedBy("freeLock")
    private boolean freeLock = true;
    @GuardedBy("lockThread")
    private Thread lockThread;

    public void getLock() {
        synchronized (lock) {
            if (freeLock) {
                freeLock = false;
                System.out.println(String.format("%s i'm lock : lock", Thread.currentThread().getName()));
                lockThread = Thread.currentThread();
            } else {
                System.out.println(String.format("%s not free lock!", Thread.currentThread().getName()));
            }
        }
    }

    public void unlock() {
        synchronized (lock) {
            if (lockThread != null && lockThread.equals(Thread.currentThread())) {
                System.out.println(String.format("%s i'm unlock : lock", Thread.currentThread().getName()));
                freeLock = true;
                lockThread = null;
            } else {
                System.out.println(String.format("%s not my lock", Thread.currentThread().getName()));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyLock myLock = new MyLock();
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                myLock.getLock();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myLock.unlock();
            }
        });
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                myLock.getLock();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myLock.unlock();
            }
        });
        threadOne.start();
        threadTwo.start();
        Thread.sleep(5000);
    }
}
