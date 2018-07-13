package ru.job4j.List;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@ThreadSafe
/**
 * Класс представляет собой динамический список типа E с базовыми функциями
 * @param <E> необходимый тип данных
 */
public class DynArray<E> implements Iterable<E> {
    @GuardedBy("this")
    // массив данных
    private Object[] container = new Object [10];
    // кол-во внесенных данных
    private int count = 0;
    // счетчик изменений контейнера
    private int modCount = 0;

    public DynArray () {
    }

    /**
     * Конструктор с начальным размером хранилища
     * @param size размер
     */
    public DynArray(int size) {
        container = new Object [size];
    }

    /**
     * Метод подсчета текущего размера хранилища данных
     * @return размер
     */
    public int size() {
        return count;
    }

    /**
     * Метод добавляет объект в хранилище (синхронизирован)
     * В случаи необходимости он увеличивает размер хранилища
     * @param obj объект для добавления
     */
    public synchronized void add(E obj) {
        if ( count == this.container.length ) {
            Object [] temp = new Object[container.length*2+1];
            System.arraycopy(container,0,temp,0,count);
            container = temp;
        }
        this.container[count++] = obj;
        modCount++;
    }

    /**
     * Метод получает объект в хранилище по нужному индексу
     * В случаи отсутствия нужного индекса выдает ошибку
     * @param index индекс хранения
     * @return объект типа E
     */
    public E get(int index) {
        if ( index < count) {
            return (E) this.container[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    /**
     *  Метод создает итератор для контейнера
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int pointer = 0;
            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return pointer < count;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    return (E) container[pointer++];
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
