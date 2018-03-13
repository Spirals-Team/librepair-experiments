package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;

//Необходимо создать контейнер с методами add(E value);E get(int index);и реализовать для него Iterable<E>
//Внутри контейнера нельзя использовать структуры данных из JDK - ArrayList. LinkedList и другие.
@ThreadSafe
public class Container<E> implements SimpleContainer<E> {

    //Внутри контейнера должен быть массив Object[] container;
    @GuardedBy("this")
    private int size;

    @GuardedBy("this")
    private int currentMaxSize = 1;

    @GuardedBy("this")
    Object[] container = new Object[currentMaxSize];

    //    Контейнер должен быть динамический. То есть метод add(E value) - может принимать бесконечное количество элементов.
    @Override
    public synchronized void add(E value) {

        if ((size + 1) > currentMaxSize) {

            currentMaxSize = currentMaxSize * 2;
            Object[] newContainer = new Object[currentMaxSize];

            for (int ind = 0; ind < container.length; ind++) {
                newContainer[ind] = container[ind];
            }

            container = newContainer;

        }

        container[size] = value;
        size++;
    }

    @Override
    public synchronized  E get(int index) {

        E returnValue = null;

        if (index < size) {
            returnValue = (E) container[index];
        }

        return returnValue;
    }

    @Override
    public synchronized  Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {

        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {

            if (!this.hasNext()) {

                throw new NoSuchElementException();
            } else {
                return (E) container[cursor++];
            }
        }
    }
}