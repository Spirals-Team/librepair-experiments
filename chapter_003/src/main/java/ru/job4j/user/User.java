package ru.job4j.user;

import java.util.Objects;

public class User implements Comparable<User> {

    private String name;
    private Integer age;

    public User(Integer age, String name) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public int compareTo(User o) {
        return Integer.compare(this.age, o.age);
    }

    @Override
    public String toString() {
        return this.name + " (" + this.age + ")";
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
        return Objects.equals(name, user.name) && age == user.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }
}