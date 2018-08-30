package ru.job4j.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class Role represent user's type.
 */
public class Role {

    private Long id;
    private String type;

    public Role() {
    }

    public Role(final String type) {
        this.type = type;
    }

    public Role(final Long id, final String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Role setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public Role setType(final String type) {
        this.type = type;
        return this;
    }

    public boolean isEmpty() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role that = (Role) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("type = " + type)
            .toString();
    }
}
