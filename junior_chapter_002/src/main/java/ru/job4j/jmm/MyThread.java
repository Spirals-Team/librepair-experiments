package ru.job4j.jmm;

public class MyThread implements Runnable {
    private Count count;

    public MyThread(Count count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            count.increase();
        }
    }
}
