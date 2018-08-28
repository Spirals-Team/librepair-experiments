
package ru.job4j.tracker.models;

/**
 * Class Task.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 03.10.2017
 */
public class Task extends Item {
    /**
     * Контруктор для объекта Task.
     *
     * @param name        имя
     * @param description дескриптор
     * @param create      create
     */
    public Task(String name, String description, long create) {
        super(name, description, create);
    }

    /**
     * Контруктор для объекта Task.
     *
     * @param name        имя
     * @param description дескриптор
     */
    public Task(String name, String description) {
        super(name, description);
    }
}