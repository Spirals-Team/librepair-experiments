package ru.job4j.user;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class User implements Comparable<User> {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = true;
        if (o == null || this.getClass() != o.getClass()) {
            result = false;
        }
        User other = (User) o;
        if (this.name.equals(other.name) || this.age == other.age) {
            result = true;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(User user) {
        return Integer.compare(this.age, user.age);
    }
}
