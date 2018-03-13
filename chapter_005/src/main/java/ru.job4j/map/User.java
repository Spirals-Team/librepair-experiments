package ru.job4j.map;

import java.util.Calendar;
import java.util.Objects;

public class User {

    String name;
    int children;
    Calendar birthday;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        User user = (User) o;
//
//        if (children != user.children) {
//            return false;
//        }
//        if (name != null ? !name.equals(user.name) : user.name != null) {
//            return false;
//        }
//
//        return birthday != null ? birthday.equals(user.birthday) : user.birthday == null;
//    }

    @Override
    public int hashCode() {

        //int result = name != null ? name.hashCode() : 0;
//        result = 31 * result + children;
//        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
//        return result;

       return Objects.hash(name, children, birthday);

    }

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }
}
