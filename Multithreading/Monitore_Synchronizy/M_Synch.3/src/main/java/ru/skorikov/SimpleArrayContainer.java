package ru.skorikov;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <E> параметр класса.
 *            <p>
 *            Создать динамический список на базе массива.
 * @ author: Alex_Skorikov.
 * @ date: 23.10.17
 * @ version: java_kurs_standart
 */
@ThreadSafe
public class SimpleArrayContainer<E> implements Iterable<E> {
    /**
     * Массив объектов.
     */
    @GuardedBy("this")
    private E[] container;
    /**
     * Счетчик.
     */
    private int index = 0;

    /**
     * Конструктор.
     *
     * @param size начальный размер массива.
     */
    @SuppressWarnings("unchecked")
    SimpleArrayContainer(int size) {
        this.container = (E[]) new Object[size];
    }

    /**
     * Добавить объект в массив.
     * Если начальный массив не вмещает новый элемент
     * создаем новый массив и котируем в него старый
     * старый массив приравниваем новому.
     *
     * @param value объект.
     */
    synchronized void add(E value) {
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
    synchronized E get(int index) {
        if (container[index] != null) {
            return container[index];
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
    public synchronized Iterator<E> iterator() {
        E[] container1 = container;
        return new Iterator<E>() {
            private int iterIndex = 0;

            @Override
            public boolean hasNext() {
                return iterIndex < container1.length;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    return container1[iterIndex++];
                }
            }
        };

    }
}