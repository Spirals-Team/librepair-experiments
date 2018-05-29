package ru.job4j.user;


import java.util.Objects;

public class User implements Comparable<User> {
    private int id;
    private String name;
    private String city;
    private int age;
    private String pasport;


    public User(String name, String passport) {
        this.name = name;
        this.pasport = passport;
    }


     public User(int id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public User() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getAge() {
        return age;
    }

    public String getPasport() {
        return pasport;
    }

    public void setPasport(String pasport) {
        this.pasport = pasport;
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
        return Objects.equals(name, user.name) && Objects.equals(pasport, user.pasport);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, pasport);
    }

    @Override
    public int compareTo(User o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", pasport='" + pasport + '\'' + '}';
    }
}
