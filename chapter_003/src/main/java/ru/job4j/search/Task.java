package ru.job4j.search;

public class Task {

    private String desc;
    private int prioriti;

    public Task(String desc, int prioriti) {
        this.desc = desc;
        this.prioriti = prioriti;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrioriti() {
        return prioriti;
    }
}
