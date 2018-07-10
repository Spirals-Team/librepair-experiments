package ru.job4j.generic;

/**
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public abstract class Base {

    private final String id;

    protected Base(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}