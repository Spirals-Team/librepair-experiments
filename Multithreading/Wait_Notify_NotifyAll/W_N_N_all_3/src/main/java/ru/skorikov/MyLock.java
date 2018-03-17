package ru.skorikov;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 09.02.18
 * @ version: java_kurs_standart
 */
public class MyLock {
    /**
     * Блокировка есть-нет?
     */
    private boolean islock;
    /**
     * Счетчик блокировок.
     */
    private AtomicInteger lockCount;
    /**
     * Поток владеющий блокировкой.
     */
    private Thread lockThread;

    /**
     * Конструктор для инициализации полей.
     */
    public MyLock() {
        this.islock = false;
        this.lockThread = null;
        this.lockCount = new AtomicInteger(0);
    }

    /**
     * Метод блокировки.
     * Синхронизация по текущему объекту этого класса.
     */
    public synchronized void lock() {
        //Текущий поток.
        Thread current = Thread.currentThread();
        //Если блокирован и текущий поток не владеет блокировкой
        while (islock && current != lockThread) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        islock = true;
        lockCount.incrementAndGet();
        lockThread = current;
    }

    /**
     * Разблокировка.
     */
    public synchronized void unlock() {
        //Если блокировкой владеет текущий поток.
        if (Thread.currentThread() == lockThread) {
            //Уменьшаем количество блокировок.
            lockCount.decrementAndGet();
            if (lockCount.get() == 0) {
                islock = false;
                lockThread = null;
                notifyAll();
            }
        }
    }
}
