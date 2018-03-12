package ru.job4j.tracker.start;

/**
 * Собственная реализация RuntimeException.
 */
public class MenuOutException extends RuntimeException {
    /**
     * Метод принимаем имя ошибки и передает его в метод RuntimeException.
     * @param message принимаем текст ошибки.
     */
    public MenuOutException(String message) {
        super(message);
    }
}
