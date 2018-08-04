package ru.job4j.tracker.menu;

import ru.job4j.tracker.dao.ITracker;
import ru.job4j.tracker.input.Input;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public interface UserAction {

    int key();

    void execute(Input input, ITracker trackerList);

    String info();
}
