package ru.job4j.search;

import java.util.Comparator;
import static java.lang.Integer.compare;

public class UserNew implements Comparable<UserNew> {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    /**
     * Переопределяем метод сравнивающий юзеров из дефолтового в сравнивающий по полю возраст .
     * @param - пользователь, с которым сравниваем
     */
    @Override
    public int compareTo(UserNew o) {
        return o.getAge() >= 0 ? compare(this.getAge(), o.getAge()) : 0;
    }
}
