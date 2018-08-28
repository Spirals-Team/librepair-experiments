package ru.job4j.automat.menu;

import ru.job4j.automat.Automat;
import ru.job4j.automat.input.Input;

/**
 * interface UserAction.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 15.10.2017
 */
public interface UserAction {
    /**
     * Номер меню.
     *
     * @return вернем уникальный номер меню.
     */
    int key();

    /**
     * Метод действия пользователя.
     *
     * @param input   объект работы с пользователем.
     * @param automat объект автомата.
     */
    void execute(Input input, Automat automat);

    /**
     * Информация о пункте меню.
     *
     * @return вернем название меню.
     */
    String info();
}
