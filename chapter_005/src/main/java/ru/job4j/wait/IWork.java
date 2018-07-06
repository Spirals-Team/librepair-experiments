package ru.job4j.wait;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public interface IWork extends Runnable {
    void execute() throws InterruptedException;
}
