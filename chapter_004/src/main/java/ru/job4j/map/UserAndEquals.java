package ru.job4j.map;

import java.util.Calendar;

public class UserAndEquals extends User {
    private String name;
    private int children;
    private Calendar birthday;

    public UserAndEquals(String name, int children, Calendar birthday) {
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
        UserAndEquals user = (UserAndEquals) obj;
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
        return super.hashCode();
    }
}
