package ru.job4j.map;

/**
 * В этом классе находятся аргументы класса ConvertList.
 *
 * @author Alexandar Vysotskiy
 * @version 1.0
 */
public class User {
    private int id;
    private String name;
    private String city;

    User(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public int getId() {
        return id;
    }
}
