package ru.job4j.tracker;

/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */

public interface UserAction {
    int key();

    void execute(Input input, Tracker tracker);

    String info();
}