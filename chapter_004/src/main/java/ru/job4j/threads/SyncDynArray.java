package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class SyncDynArray<E> implements Iterable<E>  {
    @GuardedBy("this")
    private Object[] container = new Object[4];
    private volatile int index = 0;
    private volatile int modCount = 0;

    /**
     * Метод добавляет элемент в динамический список, путем увеличения длины массива и копирования
     * в него старого массива
     * @param value - добавляемый элемент.
     */
    public synchronized void add(E value) {
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
    public synchronized E get(int index) {
        return (E) container[index];
    }

    /**
     * Итератор для динамического списка на базе массива
     * Зависит от того, где в коде создаем итератор-на том месте фиксирует число структурных изменений и при
     * изменении их числа генерирует ConcurrentModificationException
     */
    @Override
    public synchronized Iterator<E> iterator() {
        int expectedModCount = modCount;
        return new Iterator<E>() {
            @Override
            public synchronized boolean hasNext()  {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return index < container.length && container[index] != null;
            }

            @Override
            public synchronized E next() {
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
