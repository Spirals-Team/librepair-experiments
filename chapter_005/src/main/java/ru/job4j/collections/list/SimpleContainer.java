package ru.job4j.collections.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Простой контейнер на базе массива.
 * @author Hincu Andrei (andreih1981@gmail.com) by 06.10.17;
 * @version $Id$
 * @since 0.1
 * @param <T> тип храннимых данных.
 */
@ThreadSafe
public class SimpleContainer<T> implements Iterable<T> {
    /**
     * Хранилище.
     */
    private T[]container;
    /**
     * Размер хранилища.
     */
    private int size = 16;
    /**
     * позиция в массиве.
     */
    private int position = 0;

    /**
     * Конструктор.
     * @param size размер.
     */
    public SimpleContainer(int size) {
        this.size = size;
        this.container = (T[]) new Object[size];
    }

    /**
     * Конструктор с длинной массива 10 ячеек.
     */
    public SimpleContainer() {

    }

    /**
     * Итератор.
     * @return способ перебора массива.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                synchronized (container) {
                    return currentIndex < size;
                }
            }

            @Override
            public T next() {
                synchronized (container) {
                    if (hasNext()) {
                        return container[currentIndex++];
                    } else {
                        throw new NoSuchElementException();
                    }
                }
            }
        };
    }

    /**
     * Добовление навых элементов с расширением массива при необходимости.
     * @param value добавляемый элемент.
     */
    @GuardedBy("container")
    public void add(T value) {
        synchronized (container) {

            if (position == size) {
                int newSize = this.size * 3 / 2 + 1;
                container = Arrays.copyOf(container, newSize);
                size = newSize;
            }
            container[position++] = value;
        }
    }

    /**
     * Получение элемента по индексу в массиве.
     * @param index индекс.
     * @return элемент.
     */
    public T get(int index) {
        synchronized (container) {
            if (index >= this.size) {
                throw new IndexOutOfBoundsException();
            } else {
                return container[index];
            }
        }
    }

    /**
     * Метод проверяет содержится ли элемент в хранилище.
     * @param value проверяемый элемент.
     * @return true or false;
     */
    public boolean contains(T value) {

            boolean found = false;
            if (container != null) {
                synchronized (container) {
                for (int i = 0; i < position; i++) {
                    T o = container[i];
                    if (o != null) {
                        if (o.equals(value)) {
                            found = true;
                            break;
                        }
                    }
                }
            }
        }
        return found;
    }
}
