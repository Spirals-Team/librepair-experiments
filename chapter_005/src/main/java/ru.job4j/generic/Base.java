package ru.job4j.generic;

public abstract class Base {

    private String id;

    public Base(String id) {
        this.id = id;
    }

    String getId() {
        return this.id;
    }

    void setId(String id) {
        this.id = id;
    }

}
