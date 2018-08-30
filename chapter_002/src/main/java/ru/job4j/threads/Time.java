package ru.job4j.threads;

import static java.lang.Thread.currentThread;

/**
 * Timer
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class Time implements Runnable {

    private long timeout;
    private CountChar counter;

    public Time(long timeout, CountChar counter) {
        this.timeout = timeout;
        this.counter = counter;
    }

    @Override
    public void run() {
        System.out.printf("Time: %s started.%n", currentThread().getName());
        Thread t = new Thread(counter);
        t.start();
        while (!currentThread().isInterrupted()) {
            if (timeout <= 0 || !t.isAlive()) {
                t.interrupt();
                currentThread().interrupt();
            } else {
                try {
                    Thread.sleep(10L);
                    timeout -= 10L;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.printf(
            "Time: %s was interrupted.%n", currentThread().getName());
    }
}
