package ru.job4j.tracker;
/**
 * MenuOutException.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class MenuOutException extends RuntimeException {
    /**
     * Constructor.
     * @param message - message
     */
    public MenuOutException(String message) {
        super(message);
    }
}
