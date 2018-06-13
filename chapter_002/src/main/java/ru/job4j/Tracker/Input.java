package ru.job4j.tracker;

/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */

public interface Input {
    String ask(String question);

    int ask(String question, int[] range);
}