package ru.job4j.tracker;

/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */

public class MenuOutException extends RuntimeException {
    public MenuOutException(String msg) {
        super(msg);
    }
}