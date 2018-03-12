package ru.job4j.automat.input;

/**
 * interface input.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 15.10.2017
 */
public interface Input {
    /**
     * Метод без реализации.
     *
     * @param question Принимаем String.
     * @return вернем String.
     */
    String ask(String question);

    /**
     * Метод без реализации.
     *
     * @return вернем String.
     */
    String getKey();

}
