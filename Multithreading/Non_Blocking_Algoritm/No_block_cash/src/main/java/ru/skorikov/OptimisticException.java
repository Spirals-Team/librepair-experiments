package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 25.02.18
 * @ version: java_kurs_standart
 */
public class OptimisticException extends Throwable {
    /**
     * Конструктор.
     */
    public OptimisticException() {
        super("Data was updated");
    }
}
