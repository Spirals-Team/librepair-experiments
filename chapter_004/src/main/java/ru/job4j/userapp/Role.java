package ru.job4j.userapp;

import static java.lang.String.format;

public enum Role {

    ADMIN, USER, GUEST;

    public static Role roleFromName(final String name) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException(
            format("Role with name: {%s}, not found", name));
    }
}
