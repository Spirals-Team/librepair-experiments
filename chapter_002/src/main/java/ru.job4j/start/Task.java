package ru.job4j.start;

/**
 * @author Sergey Afonin (mailto:afonin1c@mail.ru)
 * @since 19.10.2017
 */
public class Task extends Item {

    /**
     * class tesk extends item.
     * @param name task name.
     * @param description task description.
     * @param create task create.
     */
    Task(String name, String description, long create) {
        super(name, description, create);
    }
}