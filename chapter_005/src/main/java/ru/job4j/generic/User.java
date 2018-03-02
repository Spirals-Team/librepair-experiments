package ru.job4j.generic;

public class User extends Base {

    private String name;

    public User(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    void setId(String id) { }
}
