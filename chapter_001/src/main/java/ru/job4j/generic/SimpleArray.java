package ru.job4j.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс представляет собой контейнер данных типа Т с базовыми функциями
 * @param <T> необходимый тип данных
 */
public class SimpleArray<T> implements Iterable<T> {

    // массив данных
    private Object [] array;
    // кол-во внесенных данных
    private int count = 0;

    /**
     * Конструктор с начальным размером хранилища
     * @param size размер
     */
    public SimpleArray (int size) {
        this.array = new Object [size];
    }

    /**
     * Метод подсчета текущего размера хранилища данных
     * @return размер
     */
    public int size() {
        return count;
    }

    /**
     * Метод добавляет объект в хранилище
     * В случаи необходимости он увеличивает размер хранилища
     * @param model объект для добавления
     */
    public void add(T model) {
        if ( count == this.array.length ) {
            Object [] temp = new Object[array.length*2+1];
            System.arraycopy(array,0,temp,0,count);
            array = temp;
        }
        this.array[count++] = model;
    }

    /**
     * Метод изменяет объект в хранилище по нужному индексу
     * В случаи отсутствия нужного индекса выдает ошибку
     * @param index индекс хранения
     * @param model новый объект
     */
    public void set(int index, T model) {
        if ( index < count) {
            this.array[index] = model;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Метод удаляет объект в хранилище по нужному индексу
     * В случаи отсутствия нужного индекса выдает ошибку
     * @param index индекс хранения
     */
    public void delete(int index) {
        if ( index < count) {
                System.arraycopy(array,index+1,array,index,count-index-1);
                count--;
        } else {
            throw new IndexOutOfBoundsException();
        }

    }

    /**
     * Метод получает объект в хранилище по нужному индексу
     * В случаи отсутствия нужного индекса выдает ошибку
     * @param index индекс хранения
     * @return объект типа T
     */
    public T get(int index) {
        if ( index < count) {
            return (T) this.array[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    /**
     *  Метод создает итератор для контейнера
     */
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int pointer = 0;

            @Override
            public boolean hasNext() {
                return pointer < count;
            }

            @Override
            public T next() {
                if (! hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) array[pointer++];
            }
        };
    }
}
