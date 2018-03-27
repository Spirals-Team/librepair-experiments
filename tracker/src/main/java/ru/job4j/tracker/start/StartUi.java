package ru.job4j.tracker.start;

import ru.job4j.tracker.connection.ConnectionSQL;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;

/**
 *StartUi .
 * @author Hincu Andrei (andreih1981@gmail.com) by 05.09.17;
 * @version $Id$
 * @since 0.1
 */
public class StartUi {
    /**
     * Input.
     */
    private Input input;
    /**
     * Tracker.
     */
    private Tracker tracker;

    /**
     * Конструктор класса.
     * @param input взаимодействие с пользователем.
     * @param tracker трекер.
     */
    public StartUi(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Start.
     * @param args отсутствует.
     */
    public static void main(String[] args) {
        new StartUi(new ValidateInput(), new Tracker(new ConnectionSQL())).init();
    }

    /**
     * Главный метод программы.
     */
    public void init() {
        MenuTracker menuTracker = new MenuTracker(this.input, this.tracker);
        menuTracker.fillActions();
        int[] rang = menuTracker.rangs();
        int key;
        do {
            menuTracker.show();
            key = input.ask("Select:", rang);
            menuTracker.select(key);
        } while (rang[6] != key);
    }

}
