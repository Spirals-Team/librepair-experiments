package ru.job4j.collections.tasktest;

/**
 * Class User.
 */
public class User {
    /**
     * @param name user name
     */
    private String name;
    /**
     * @param user passport
     */
    private String passport;

    /**
     * Constructor.
     * @param name user name
     * @param passport user passport
     */
    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    /**
     * Getter name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Override equals.
     * @param o User
     * @return result
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

        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }

        return passport != null ? passport.equals(user.passport) : user.passport == null;
    }

    /**
     * Override hashCode.
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        return result;
    }
}
