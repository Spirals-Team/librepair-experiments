package ru.job4j.collections.task331;

/**
 *Class User.
 *@author ifedorenko
 *@since 08.09.2017
 *@version 1
 */
public class User implements Comparable<User> {
    /**
     * @param name user name
     */
    private String name;
    /**
     * @param age user age
     */
    private int age;

    /**
     * Constructor.
     * @param name user name
     * @param age user age
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Override method from Comparable.
     * @param o object of compare
     * @return result compared
     */
    @Override
    public int compareTo(User o) {
        return this.age == o.age ? 0 : this.age > o.age ? 1 : -1;
    }

    /**
     * Getter age.
     * @return age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Getter name.
     * @return name
     */
    public String getName() {
        return this.name;
    }
}
