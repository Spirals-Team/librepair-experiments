package ru.job4j.tracker.start;

import java.util.ArrayList;

/**
 * interface input.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 07.10.2017
 */
public interface Input {
    /**
     * Метод без реализации.
     * @param question Принимаем String.
     * @return вернем String.
     */
    String ask(String question);
    /**
     * Метод без реализации.
     * @param question Принимаем String.
     * @param range Принимаем массив int.
     * @return вернем int.
     */
    int ask(String question, ArrayList<Integer> range);

    /**
     * Метод без реализации.
     * @return вернем String.
     */
    String getKey();

}
