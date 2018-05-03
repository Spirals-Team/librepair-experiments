package ru.job4j.prof.dopclass;

/**
 * материнский класс для классов студент и пациент
 */

public class Human {
    private String name;
    private Sex sex;
    private int age;

    public Human(String name, Sex sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public Sex getSex() {
        return this.sex;
    }

    public int getAge() {
        return this.age;
    }
}
