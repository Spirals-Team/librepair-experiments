package ru.job4j.tracker;
/**
 * Interface UserAction.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public interface UserAction {
    /**
     * Execute menu's item.
     * @param input - input
     * @param tracker - tracker
     */
    void execute(Input input, Tracker tracker);
    /**
     * Show menu item.
     * @return - string
     */
    String info();
}
