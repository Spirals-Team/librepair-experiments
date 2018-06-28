package ru.job4j.collections;

import ru.job4j.iterator.SimpleArray;

import java.util.Iterator;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleSet<T> implements Iterable<T> {

    /**
     * Коллекция на основе другой коллекции, использующей в своей основе массив. с помощью композиции
     * внедрили коллецкию SimpleArray и использовали ее методы для сокращения кода данной коллекции.
     */
    private SimpleArray<Object> array = new SimpleArray<>();
    private int index = 0;
    private int size = 0;

    /**
     * Метод добавляет элемент типа Т в коллекцию, используя композицию,
     * если элемент уже содержится в коллекции, то добавления не происходит, таким
     * образом реализуется множество элементов. Внутри элементы располагаются в порядке добавления в коллекцию
     * @param  t - добавляемый элемент коллекции.
     */
    public void add(T t) {
        boolean flag = true;
        for (int i = 0; i < size; i++) {
            if (array.get(i).equals(t)) {
                flag = false;
            }
        }
        if (flag) {
            array.add(t);
            size++;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                return array.iterator().hasNext();
            }

            @Override
            public T next() {
                return (T) array.iterator().next();
            }
        };
    }
}
