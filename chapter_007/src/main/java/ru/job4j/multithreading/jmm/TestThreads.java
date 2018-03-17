package ru.job4j.multithreading.jmm;

/**
 * Илюстрация проблем многопоточности..
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 07.11.17;
 * @version $Id$
 * @since 0.1
 */
public class TestThreads {
    public static void main(String[] args) {
        Counter counter = new Counter();
        for (int i = 0; i < 100; i++) {
            CounterThread t = new CounterThread(counter);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long l = counter.getCount();
        System.out.println(l);
        // ответы всегда разные и иногда  равны 10000 как ожидается
    }
}
