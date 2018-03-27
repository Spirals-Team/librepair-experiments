package ru.job4j.collections.generic;

/**
 * Абстрактный класс Base.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 05.10.17;
 * @version $Id$
 * @since 0.1
 */
public abstract class Base {
    /**
     * Геттер.
     * @return возврат id элемента.
     */
    abstract String getId();

    /**
     * Сеттер.
     * @param id установка id.
     */
    abstract void setId(String id);
}
