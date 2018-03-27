package ru.job4j.litle.user;
/**
 * User.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 18.09.17;
 * @version $Id$
 * @since 0.1
 */
public class User implements Comparable<User> {
    /**
     * имя.
     */
    private String name;
    /**
     * возраст.
     */
    private int age;

    /**
     * конструктор.
     * @param name имя.
     * @param age возраст.
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * как сравнивать обьекты.
     * @param user user.
     * @return -1, 0, 1.
     */
    @Override
    public int compareTo(User user) {
        return this.age - user.age;
    }

    /**
     * toString.
     * @return name + age.
     */
    @Override
    public String toString() {
        return "User{"
                + "name='"
                + name
                + '\''
                + ", age="
                + age
                + '}';
    }

    /**
     * getter.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * getter.
     * @return age.
     */
    public int getAge() {
        return age;
    }

    /**
     * equals.
     * @param o o.
     * @return true or false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (age != user.age) {
            return false;
        }
        return name != null ? name.equals(user.name) : user.name == null;
    }

    /**
     * hashcode.
     * @return code.
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}
