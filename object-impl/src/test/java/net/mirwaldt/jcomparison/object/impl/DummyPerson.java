package net.mirwaldt.jcomparison.object.impl;

public class DummyPerson {

    enum Sex {
        MALE, FEMALE
    }

    private final String name;
    private final int age;
    private final Sex sex;

    private final DummyAddress address;

    public DummyPerson(String name, int age, Sex sex, DummyAddress address) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public DummyAddress getAddress() {
        return address;
    }
}
