package ru.curriculum.web;

import lombok.NonNull;

public class Redirect {
    public static String redirectTo(@NonNull String route) {
        return "redirect:".concat(route);
    }
}
