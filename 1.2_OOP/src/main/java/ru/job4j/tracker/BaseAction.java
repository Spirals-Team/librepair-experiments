package ru.job4j.tracker;
/**
 *Абстрактный класс BaseAction.
 *@author ifedorenko
 *@since 30.08.2017
 *@version 1
 */
public abstract class BaseAction implements UserAction {
    /**
     * @param key ключ опции
     */
    private int key;
    /**
     * @param name описание пункта меню.
     */
    private String name;
    /**
     * КОнструктор.
     * @param key ключ опции
     * @param name описание пункта меню
     */
    public  BaseAction(int key, String name) {
        this.key = key;
        this.name = name;
    }
    /**
     * Информация о пункет меню.
     * @return то делает меню.
     */
    public String info() {
            return String.format("%s. %s", this.key(), this.name);
    }
    /**
     * Ключ операции.
     * @return ключ
     */
    public int key() {
        return this.key;
    }
}
