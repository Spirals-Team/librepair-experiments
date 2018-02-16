package ru.job4j.sortuser;
/**
 * User.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class User implements Comparable<User> {
    /*** User name.*/
    private String name;
    /*** User age.*/
    private Integer age;
    /**
     * Constructor.
     * @param name - name
     * @param age - age
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    /**
     * Get name.
     * @return - name
     */
    public String getName() {
        return name;
    }
    /**
     * Get age.
     * @return - age
     */
    public Integer getAge() {
        return age;
    }

    @Override
    public int compareTo(User o) {
        return this.age.compareTo(o.age) == 0 ? this.name.compareTo(o.name) : this.age.compareTo(o.age);
    }

    @Override
    public String toString() {
        return name + " " + age;
    }
}
