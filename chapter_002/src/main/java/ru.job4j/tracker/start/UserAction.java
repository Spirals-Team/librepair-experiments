package ru.job4j.tracker.start;

/**
 * interface UserAction.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 11.10.2017
 */
public interface UserAction {
    /**
     * Номер меню.
     * @return вернем уникальный номер меню.
     */
    int key();

    /**
     * Метод действия пользователя.
     * @param input объект работы с пользователем.
     * @param tracker объект трекера.
     */
    void execute(Input input, Tracker tracker);

    /**
     * Информация о пункте меню.
     * @return вернем название меню.
     */
    String info();
}
