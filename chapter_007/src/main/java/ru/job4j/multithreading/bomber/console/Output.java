package ru.job4j.multithreading.bomber.console;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 24.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public interface Output {
    /**
     * Метод задает случайное число
     * @param from от числа.
     * @param to до числа.
     * @return число.
     */
    int getRandomInt(int from, int to);
}
