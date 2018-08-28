package ru.job4j.collection;

/**
 * Класс User.
 */
public class User {
    /**
     * id.
     */
    private int id;
    /**
     * name.
     */
    private String name;
    /**
     * city.
     */
    private String city;

    /**
     * Конструктор.
     * @param id id.
     * @param name name.
     * @param city city.
     */
    public User(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    /**
     * getId.
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * setId
     * @param id id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getName.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * setName.
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getCity.
     * @return city.
     */
    public String getCity() {
        return city;
    }

    /**
     * setCity.
     * @param city city.
     */
    public void setCity(String city) {
        this.city = city;
    }
}
