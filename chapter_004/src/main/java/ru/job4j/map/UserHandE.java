package ru.job4j.map;

import java.util.Calendar;

public class UserHandE extends User {
    private String name;
    private int children;
    private Calendar birthday;

    public UserHandE(String name, int children, Calendar birthday) {
        super(name, children, birthday);
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UserHandE user = (UserHandE) obj;
        if (user.getBirthday() == null
                && this.birthday == null
                && user.getName().equals(this.name)
                && user.getChildren() == this.children) {
            return true;
        }
        return user.getName().equals(this.name)
                && user.getBirthday().equals(this.birthday)
                && user.getChildren() == this.children;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.children;
    }
}
