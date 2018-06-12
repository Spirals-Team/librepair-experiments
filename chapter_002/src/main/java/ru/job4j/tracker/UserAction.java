package ru.job4j.tracker;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public interface UserAction {

    int key();

    void execute(Input input, Tracker tracker);

    String info();
}
