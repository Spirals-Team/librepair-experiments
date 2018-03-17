package ru.skorikov;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <E> параметр класса.
 *
 * Создать динамический список на базе массива.
 * @ author: Alex_Skorikov.
 * @ date: 10.01.18
 * @ version: java_kurs_standart
 */

public class SimpleArrayContainer<E> implements Iterable<E> {
    /**
     * Массив объектов.
     */
    private Object[] container;
    /**
     * Счетчик.
     */
    private int index = 0;

    /**
     * Конструктор.
     *
     * @param size начальный размер массива.
     */
    public SimpleArrayContainer(int size) {
        this.container = new Object[size];
    }

    /**
     * Добавить объект в массив.
     * Если начальный массив не вмещает новый элемент
     * создаем новый массив и котируем в него старый
     * старый массив приравниваем новому.
     *
     * @param value объект.
     */
    public void add(E value) {
        if (index < container.length) {
            container[index++] = value;
        } else {
            container = Arrays.copyOf(container, container.length * 2);
            container[index++] = value;
        }
    }

    /**
     * Получить объект из массива.
     *
     * @param index индекс объекта.
     * @return объект.
     */
    E get(int index) {
        if (container[index] != null) {
            return (E) container[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Итератор для перемещения по коллекции.
     *
     * @return итератор.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int iterIndex = 0;

            @Override
            public boolean hasNext() {
                return iterIndex < container.length;
            }
            @Override
            public E next() {
                if (hasNext()) {
                    return (E) container[iterIndex++];
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}

