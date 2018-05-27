package ru.job4j.bank;

import java.util.Objects;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class User {
    private String name;
    private String pasport;

    public User(String name, String pasport) {
        this.name = name;
        this.pasport = pasport;
    }

    public String getName() {
        return name;
    }

    public String getPasport() {
        return pasport;
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
        return Objects.equals(this.pasport, user.pasport) && Objects.equals(this.name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pasport);
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.name, this.pasport);
    }
}
