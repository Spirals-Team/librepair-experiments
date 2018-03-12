package ru.job4j.jmm;

public class Count {
    private int count;

    public void increase() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
