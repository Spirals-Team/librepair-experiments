package ru.curriculum.domain.admin.user.exceptions;

public class IllegalPassword extends RuntimeException {
    public IllegalPassword() {
        super("Password length must be grater than 3");
    }
}
