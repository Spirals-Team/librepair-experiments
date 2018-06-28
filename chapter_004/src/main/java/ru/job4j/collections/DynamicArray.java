package ru.job4j.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class DynamicArray<E> implements Iterable<E>  {

    private Object[] container = new Object[4];
    private int index = 0;
    private int modCount = 0;

    /**
     * Метод добавляет элемент в динамический список, путем увеличения длины массива и копирования
     * в него старого массива
     * @param value - добавляемый элемент.
     */
    public void add(E value) {
        if (modCount > container.length - 1) {
        double threshold = container.length * 1.5;
        Object[] copy = new Object[(int) threshold];
        System.arraycopy(container, 0, copy, 0, container.length);
        copy[modCount] = value;
        container = copy;

        } else {
            container[modCount] = value;
        }
        modCount++;
    }

    /**
     * Метод возвращает элемент по его индексу
     * @param index - место элемента в массиве.
     * @return элемент массива
     */
    public E get(int index) {
        return (E) container[index];
    }

    /**
     * Итератор для динамического списка на базе массива
     * Зависит от того, где в коде создаем итератор-на том месте фиксирует число структурных изменений и при
     * изменении их числа генерирует ConcurrentModificationException
     */
    @Override
    public Iterator<E> iterator() {
        int expectedModCount = modCount;
        return new Iterator<E>() {
            @Override
            public boolean hasNext()  {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return index < container.length && container[index] != null;
            }

            @Override
            public E next() {
                if (this.hasNext()) {
                    Object res = container[index];
                    index++;
                    return (E) res;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}