package ru.job4j.tracker;
/**
 * BaseAction.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public abstract class BaseAction implements UserAction {
    /*** Actions name.*/
    private String name;
    /*** Actions key.*/
    private int key;
    /**
     * Constructor.
     * @param key - key
     * @param name - name
     */
    BaseAction(int key, String name) {
        this.name = name;
        this.key = key;
    }
    /**
     * Show menu item.
     * @return - string
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key, this.name);
    }
}
