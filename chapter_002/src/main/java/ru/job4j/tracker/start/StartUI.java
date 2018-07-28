package ru.job4j.tracker.start;

import ru.job4j.tracker.dao.ITracker;
import ru.job4j.tracker.dao.TrackerList;
import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.menu.MenuTracker;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class StartUI {
    /**
     * Получение данных от пользователя.
     */
    private final Input input;
    /**
     * Хранилище заявок.
     */
    private final ITracker tracker;

    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, ITracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основой цикл программы.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions();
        boolean key;
        do {
            menu.show();
            key = menu.select(input.ask("Выберите пункт меню: ", menu.getRanges()));
        } while (key);
    }
}
