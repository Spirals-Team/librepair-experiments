package ru.job4j.threads;

public class OptimisticException extends RuntimeException  {
    public OptimisticException(String msg) {
        super(msg);
    }
}
