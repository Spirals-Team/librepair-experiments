package ru.job4j.start;

/**
 * 1 Написать интерфейс UserAction, в котором определить методы, общие для всех событий.
 */
public interface UserAction {

    int key();

    void execute(Input input, Tracker tracker);

    String info();
}
