package ru.job4j.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс реализует тестовую задачу Switcher
 */
public class Switcher {
    //количество символов передаваемых потоком за один цикл
    private static final int COUNT = 10;
    private final StringBuffer string = new StringBuffer();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condA = lock.newCondition();
    private final Condition condB = lock.newCondition();

    /**
     * Метод возвращет текущую строку
     * @return строка
     */
    public StringBuffer getString() {
        return string;
    }

    /**
     * Метод добавляет число в строку
     * @param number число
     */
    public void add(int number) {
        string.append(number);
    }

    /**
     * Поток, добавляющий 1 к строке 10 раз
     */
    protected Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                for (int i = 0; i < COUNT; i++) {
                    add(1);
                }
                System.out.println("Thread1 - current string - "+string);
                condB.signalAll();
                try {
                    condA.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                lock.unlock();
            }
            System.out.println("Thread1 end");
        }
    });

    /**
     * Поток, добавляющий 2 к строке 10 раз
     */
    protected  Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                try {
                    condB.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    continue;
                }
                for (int i = 0; i < COUNT; i++) {
                    add(2);
                }
                System.out.println("Thread2 - current string - "+ string);
                condA.signalAll();
                lock.unlock();
            }
            System.out.println("Thread2 end");
        }
    });

}
