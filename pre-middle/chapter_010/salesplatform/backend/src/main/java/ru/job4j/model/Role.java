package ru.job4j.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class represent Role.
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

//    enum String {
//        USER,
//        GUEST;
//
//        public Type typeFromTitle(final String title) {
//            for (Type type : Type.values()) {
//                if (type.name().equalsIgnoreCase(title)) {
//                    return type;
//                }
//            }
//            throw new IllegalArgumentException(
//                String.format("Unknown Role.Type with Title: %s", title));
//        }
//    }
}
