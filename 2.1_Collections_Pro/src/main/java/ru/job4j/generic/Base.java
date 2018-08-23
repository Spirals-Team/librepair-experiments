package ru.job4j.generic;

/**
 * Class Base.
 *@author ifedorenko
 *@since 25.09.2017
 *@version 1
 */
public abstract class Base {
    /**
     * @param id Base id
     */
    private String id;

    /**
     * Constructor.
     * @param id id
     */
    public Base(String id) {
        this.id = id;
    }

    /**
     * Setter.
     * @param id id for setting.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter.
     * @return id
     */
    public String getId() {
        return id;
    }
}
