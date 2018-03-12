package ru.job4j.tracker.models;

/**
 * Class Item.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 29.09.2017
 */
public class Item {
    /**
     * id.
     */
    private String id;
    /**
     * name.
     */
    private String name;
    /**
     * description.
     */
    private String description;
    /**
     * create.
     */
    private long create;

    /**
     * Контруктор для объекта Item.
     *
     * @param name        имя
     * @param description дескриптор
     * @param create      create
     */
    public Item(String name, String description, long create) {
        this.name = name;
        this.description = description;
        this.create = create;
    }

    /**
     * Контруктор для объекта Item.
     *
     * @param name        имя
     * @param description дескриптор
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Геттер для name.
     *
     * @return вернем name
     */
    public String getName() {
        return name;
    }

    /**
     * Геттер для description.
     *
     * @return вернем description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Геттер для create.
     *
     * @return вернем create
     */
    public long getCreate() {
        return create;
    }

    /**
     * Геттре для id.
     *
     * @return вернем id
     */
    public String getId() {
        return id;
    }

    /**
     * Сеттер для id.
     *
     * @param id принимаем String как входящий параметр
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " Name: " + getName()
                + " Description: " + getDescription() + " Create: " + getCreate();
    }
}
