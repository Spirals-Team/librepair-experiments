package ru.job4j.map;

import java.util.Calendar;

public class UserAndHash extends User {
    private String name;
    private int children;
    public UserAndHash(String name, int children, Calendar birthday) {
        super(name, children, birthday);
        this.name = name;
        this.children = children;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.children;
    }
}
