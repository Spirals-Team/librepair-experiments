package ru.job4j.users.model;

import java.util.Objects;

/**
 * The type Role.
 */
public class Role {
    /**
     * ID.
     */
    private int id;
    /**
     * Name.
     */
    private String name;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Role.
     *
     * @param id   the id
     * @param name the name
     */
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return getId() == role.getId() &&
                Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
