package ru.job4j.multithreading;

/**
 * импровизированный лок.
 * @author Hincu Andrei (andreih1981@gmail.com)on 14.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class LockEx {
    /**
     * Флаг.
     */
    private boolean take = false;

    /**
     * метод блокировки.
     */
    public synchronized void lock() {
        while (take) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        take = true;
    }

    /**
     * метод снятия блока.
     */
    public synchronized void unLock() {
        take = false;
        notify();
    }
}

/**
 * Тест Lock.
 */
class TestLockEx {
    public static void main(String[] args) {
        LockEx lockEx = new LockEx();
        Resurs resurs = new Resurs();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new TestThread(resurs, lockEx));
            t.setName("Thread" + i);
            t.start();
        }
    }
}

/**
 * Класс общего ресурса.
 */
class Resurs {
    private int x = 0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}

/**
 * класс потоков изменяющих ресурс.
 */
class TestThread implements Runnable {
    private Resurs r;
    private LockEx lockEx;

    public TestThread(Resurs r, LockEx lockEx) {
        this.r = r;
        this.lockEx = lockEx;
    }

    @Override
    public void run() {
        lockEx.lock();
        r.setX(0);
        for (int i = 1; i < 6; i++) {
            System.out.println(Thread.currentThread().getName() + " " + r.getX());
            r.setX(i);
        }
        lockEx.unLock();
    }
}