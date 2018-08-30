package ru.job4j.map;

import java.util.Calendar;
import java.util.Objects;

/**
 * Class represent model User.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class User {

    String name;
    int children;
    Calendar birthday;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return children == user.children
            && Objects.equals(name, user.name)
            && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + children;
        result = 31 * result + name.hashCode();
        result = 31 * result + birthday.hashCode();
        return result;
    }
}
