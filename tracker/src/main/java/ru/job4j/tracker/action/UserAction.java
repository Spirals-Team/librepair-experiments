package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.start.Tracker;

/**
 * UserAction.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 07.09.17;
 * @version $Id$
 * @since 0.1
 */
public interface UserAction {
    /**
     * Получает ключь(ответ пользователя).
     * @return key.
     */
    int key();

    /**
     * Метод выполнения команды.
     * @param tracker tracker.
     * @param input input.
     */
    void execute(Tracker tracker, Input input);

    /**
     * Распечатывает инфо о событии.
     * @return сообщение пользователю.
     */
    String info();

}
