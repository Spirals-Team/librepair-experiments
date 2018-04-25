package ru.aserdyuchenko.testTask;

/**
 * Created by anton on 05.05.17.
 */
public class User {
    /**
     * @param name - user name.
     */
    private String name;
    /**
     * @param passport - user passport.
     */
    private int passport;

    /**
     * Constructor.
     * @param userName - user name.
     * @param userPassport - user passport.
     */
    public User(String userName, int userPassport) {
        this.name = userName;
        this.passport = userPassport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (passport != user.passport) return false;
        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + passport;
        return result;
    }

    public String getName() {
        return name;
    }
}
