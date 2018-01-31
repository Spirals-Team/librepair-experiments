package ru.javawebinar.topjava.repository;

/** @author danis.tazeev@gmail.com */
public final class DuplicateException extends Exception {
    public DuplicateException(String msg) {
        super(msg);
    }
}
