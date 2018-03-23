package ru.job4j.jmm;

public class Counter {
    private long c = 0;

    public void increment() {
        c++;
    }

    public void decrement() {
        c--;
    }

    public long value() {
        return c;
    }
}
