package ru.job4j.generic;

/**
 * Абстрактный родительский класс
 */
public abstract class Base {
    private final String id;

    /**
     * Конструктор с параметром
     * @param id параметр
     */
    protected Base(final String id) {
        this.id = id;
    }

    /**
     *Гет параметра
     * @return параметр
     */
    public String getId() {
        return id;
    }
}
