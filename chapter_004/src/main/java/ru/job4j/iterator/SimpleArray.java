package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleArray<T> implements Iterable {

    private Object[] array = new Object[0];
    private int index = 0;

    /**
     * Метод добавляет элемент типа Т в массив, путем создания нового массива
     * бОльшего размера и копирования в него старого плюс добавление в конец массива элемента.
     * @param  model - добавляемый элемент массива.
     */
    public void add(T model) {
        Object[] copy = new Object[array.length + 1];
        System.arraycopy(array, 0, copy, 0, array.length);
        copy[copy.length - 1] = model;
        array = copy;
    }

    /**
     * Метод устанавливает новый элемент на заданное место
     * @param index - место, в которое нужно добавить элемент в массиве.
     * @param model - элемент, который добавляем
     */
    public void set(int index, T model) {
        if (index >= 0 && index < array.length) {
            array[index] = model;
        }
    }

    /**
     * Метод удаляет элемент из массива по номеру его места в массиве путем создания нового массива размером меньшим на 1 элемент
     * и последующим копированием в него элементов до индекса и после
     * @param index - позиция удаляемого элемента.
     * @return новый массив
     */
    public T[] delete(int index) {
        Object[] copy = new Object[array.length - 1];
        if (index >= 0 && index < array.length) {
            System.arraycopy(array, 0, copy, 0, index);
            System.arraycopy(array, index + 1, copy, index, array.length - index - 1);
        }
        array = copy;
        return (T[]) copy;
    }

    /**
     * Метод возвращает элемент по его индексу
     * @param index - место элемента в массиве.
     * @return элемент массива
     */
    public T get(int index) {
        return (T) array[index];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            /**
             * Метод возвращает true если есть следующий элемент в массиве сравнивая индекс и длину массива
             */
            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            /**
             * Метод возвращает элемент находящийся справа от коретки, при условии, что hasNext вернуло истину
             * иначе кидает эксепшн
             */
            @Override
            public T next() {
                if (this.hasNext()) {
                    Object res = array[index];
                    index++;
                    return (T) res;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
