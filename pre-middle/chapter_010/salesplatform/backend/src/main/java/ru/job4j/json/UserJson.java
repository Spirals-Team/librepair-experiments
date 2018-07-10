package ru.job4j.json;

import ru.job4j.model.User;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represent User class.
 */
public class UserJson {

    private User user;

    public UserJson() {
    }

    public UserJson(final User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public UserJson setUser(final User user) {
        this.user = user;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserJson that = (UserJson) o;
        return Objects.equals(this.user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("user = " + user)
            .toString();
    }
}
