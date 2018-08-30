package ru.job4j.jmm;

import static java.lang.Thread.currentThread;

public class RaceCondition implements Runnable {

    private int attempt;
    private SharedObject sharedObject;

    public RaceCondition(final int attempt, final SharedObject sharedObject) {
        this.attempt = attempt;
        this.sharedObject = sharedObject;
    }

    @Override
    public void run() {
        System.out.printf("start: %s%n", currentThread().getName());
        for (int i = 0; i < attempt; i++) {
            sharedObject.increment();
            try {
                Thread.currentThread().sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("finish: %s%n", currentThread().getName());
    }
}
