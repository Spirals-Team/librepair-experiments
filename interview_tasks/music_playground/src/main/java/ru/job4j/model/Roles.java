package ru.job4j.model;

import static java.lang.String.format;

public enum Roles {

    ADMIN, MANDATOR, USER;

    public static Roles roleFromName(final String name) {
        for (Roles role : Roles.values()) {
            if (role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException(
            format("Role with name: {%s}, not found", name));
    }
}
