package ru.skorikov;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created with IntelliJ IDEA.
 *
 * @param <E> параметр класса.
 *            <p>
 *            Реализовать коллекцию Set на массиве
 * @ author: Alex_Skorikov.
 * @ date: 01.11.17
 * @ version: java_kurs_standart
 */


public class SimpleSet<E> implements Iterator<E> {
    /**
     * Хранилище объектов.
     */
    private Object[] simpleSet;
    /**
     * Номер в хранилище.
     */
    private int index;
    /**
     * Переменная итератора.
     */
    private int iteratorIndex = 0;

    /**
     * Конструктор.
     *
     * @param size начальный размер хранилища.
     */
    public SimpleSet(int size) {
        this.simpleSet = new Object[size];
    }

    /**
     * Проверка наличия дубликата.
     *
     * @param data - element to search
     * @return - true - has duplicate, false - otherwise.
     */
    private boolean hasDuplicate(E data) {
        boolean hasDublicate = false;
        for (int i = 0; i < index; i++) {
            if (simpleSet[i].equals(data)) {
                hasDublicate = true;
                break;
            }
        }
        return hasDublicate;
    }

    /**
     * Хватает ли места в массиве.
     */
    private void ensureCapacity() {
        if (index >= simpleSet.length) {
            simpleSet = Arrays.copyOf(simpleSet, simpleSet.length * 2);
        }
    }

    /**
     * Добавить объект в хранилище.
     *
     * @param data объект.
     */
    public void add(E data) {
        if (!hasDuplicate(data)) {
            ensureCapacity();
            simpleSet[index++] = data;
        }
    }

    @Override
    public boolean hasNext() {
        return iteratorIndex < index;
    }

    @Override
    public E next() {
        if (hasNext()) {
            return (E) simpleSet[iteratorIndex++];
        } else {
            throw new NoSuchElementException("No element");
        }
    }

    /**
     * Получить коллекцию.
     *
     * @return коллекция.
     */
    public Object[] getSimpleSet() {
        return simpleSet;
    }

    /**
     * Получить индекс.
     *
     * @return индекс.
     */
    public int getIndex() {
        return index;
    }
}
