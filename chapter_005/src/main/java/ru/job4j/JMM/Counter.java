package ru.job4j.JMM;

public class Counter implements Runnable {
    int count = 0;

    public void increment() {
        count = count + 1;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            increment();
        }
    }
}
