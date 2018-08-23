package ru.job4j.nonblock;

/**
 * Class OptimisticException.
 */
public class OptimisticException extends RuntimeException {
    /**
     * Constructor.
     * @param message message about exception
     */
    public OptimisticException(String message) {
        super(message);
    }
}
