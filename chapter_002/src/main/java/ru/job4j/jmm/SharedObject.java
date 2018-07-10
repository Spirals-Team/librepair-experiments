package ru.job4j.jmm;

public class SharedObject {

    private Long num;

    public SharedObject(final Long num) {
        this.num = num;
    }

    public Long getNum() {
        return num;
    }

    public void increment() {
        num++;
    }
}
