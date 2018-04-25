package ru.aserdyuchenko.generic.task_5_2_2;

/**
 * @author anton.
 */
public abstract class Base {
    /**
     * @param id - base id.
     */
    private String id;

    /**
     * Constructor.
     * @param id - base id.
     */
    public Base(String id) {
        this.id = id;
    }
    /**
     * Method get id.
     * @return id.
     */
    public String getId() {
        return this.id;
    }
}
