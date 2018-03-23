package ru.job4j.bankmanage;
/**
 * User.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class User {
    /*** User name.*/
    private String name;
    /*** User passport.*/
    private Integer passport;
    /**
     * Constructor.
     * @param name - name
     * @param passport - passport
     */
    public User(String name, Integer passport) {
        this.name = name;
        this.passport = passport;
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
        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        return passport != null ? passport.equals(user.passport) : user.passport == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        return result;
    }
}
