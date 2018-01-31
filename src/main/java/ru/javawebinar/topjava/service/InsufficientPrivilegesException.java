package ru.javawebinar.topjava.service;

/** @author danis.tazeev@gmail.com */
final class InsufficientPrivilegesException extends RuntimeException {
    InsufficientPrivilegesException(String msg) { super(msg); }
}
