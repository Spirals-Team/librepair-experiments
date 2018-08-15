package ru.job4j.collections.task322;

/**
 * Class User.
 */
public class User {
    /**
     * @param id user id
     */
    private int id;
    /**
     * @param name user name
     */
    private String name;
    /**
     * @param city user city
     */
    private String city;

    /**
     * Constructor.
     * @param id user id
     * @param name user name
     * @param city user city
     */
    public  User(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    /**
     * Getter id.
     * @return id
     */
    public int getId() {
        return id;
    }
}
