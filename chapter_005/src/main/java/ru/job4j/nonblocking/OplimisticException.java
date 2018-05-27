package ru.job4j.nonblocking;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class OplimisticException extends Throwable {
    public OplimisticException(String message) {
        super(message);
    }
}
