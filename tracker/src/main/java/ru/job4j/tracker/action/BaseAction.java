package ru.job4j.tracker.action;

/**
 * BaseAction.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 08.09.17;
 * @version $Id$
 * @since 0.1
 */
public abstract class BaseAction implements UserAction {
    /**
     * Имя подменю.
     */
    private String name;
    /**
     * Номер подменю.
     */
    private int key;

    @Override
    public int key() {
        return key;
    }

    /**
     * Конструктор.
     * @param name имя подменю.
     * @param key номер подменю.
     */
    public BaseAction(String name, int key) {
        this.name = name;
        this.key = key;
    }

    /**
     * Метод инфо одинаков для всех классов.
     * @return инфо в меню
     */
    @Override
    public String info() {
        return String.format("%d. %s", key, name);
    }
}
