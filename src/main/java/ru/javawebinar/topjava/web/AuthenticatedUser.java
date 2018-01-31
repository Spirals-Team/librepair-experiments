package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.User;

import java.util.Objects;

public final class AuthenticatedUser {
    private AuthenticatedUser() {}

    private static User user;

    public static User getUser() { return user; }
    public static void setUser(User user) {
        Objects.requireNonNull(user, "user");
        AuthenticatedUser.user = user;
    }
}
