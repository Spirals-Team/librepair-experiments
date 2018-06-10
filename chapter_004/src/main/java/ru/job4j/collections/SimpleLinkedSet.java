package ru.job4j.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleLinkedSet<T> implements Iterable<T> {

    private Node<T> fstNode;
    private Node<T> lstNode;
    private int size = 0;

    /**
     * Конструктор для пустой ноды, содержит первый и последний элемент null, ссылающиеся друг на друга
     */
    public SimpleLinkedSet() {
        lstNode = new Node(null, fstNode, null);
        fstNode = new Node(null, null, lstNode);
    }

    /**
     * Метод добавляет элемент в контейнер записывая значение в хвост и создадает новый нулевой хвост,
     * который ссылается на старый хвост с новым значением, если элемент уже содержится в коллекции, то его добавление не происходит,
     * тем самым реализуется множество значений в коллекции
     * @param value - элемент, который записываем в конец связанного списка.
     */
    public void add(T value) {
        Node<T> target = fstNode;
        for (int i = 0; i < size; i++) {
            target = target.next;
            if (target.item.equals(value)) {
                return;
            }
        }
        Node<T> prev = lstNode;
        prev.item = value;
        lstNode = new Node(null, prev, null);
        prev.next = lstNode;
        size++;
    }

    public class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Итератор для динамического списка на базе связанного списка
      */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int counter = 0;
            Node<T> target = fstNode.next;
            @Override
            public boolean hasNext() {
                return counter < size;
            }

            @Override
            public T next() {
                if (this.hasNext()) {
                    T result = target.item;
                    counter++;
                    target = target.next;
                    return result;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
