package ru.job4j.cache;

/**
 * Thrown when there's an error during operation in non-blocking cache.
 */
public class OptimisticException extends RuntimeException {

    public OptimisticException(Throwable cause) {
        super(cause);
    }
}
