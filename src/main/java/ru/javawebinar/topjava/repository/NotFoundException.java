package ru.javawebinar.topjava.repository;

/** @author danis.tazeev@gmail.com */
public final class NotFoundException extends Exception {
    public NotFoundException(String msg) {
        super(msg);
    }
}
