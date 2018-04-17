package ru.job4j.bank;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class User implements Comparable<User> {
    private String name;
    private String passport;

    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    @Override
    public int compareTo(User user) {
        return this.getPassport().compareTo(user.getPassport());
    }

    public String getPassport() {
        return passport;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
