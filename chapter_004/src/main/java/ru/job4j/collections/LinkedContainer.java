package ru.job4j.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class LinkedContainer<E> implements Iterable<E> {
    private Node<E> fstNode;
    private Node<E> lstNode;
    private int size = 0;
    private int modCount = 0;

    /**
     * Конструктор для пустой ноды, содержит первый и последний элемент null, ссылающиеся друг на друга
     */
    public LinkedContainer() {
       lstNode = new Node(null, fstNode, null);
       fstNode = new Node(null, null, lstNode);
    }

    /**
     * Метод добавляет элемент в контейнер записывая значение в хвост и создадает новый нулевой хвост,
     * который ссылается на старый хвост с новым значением
     * @param value - элемент, который записываем в конец связанного списка.
     */
    public void add(E value) {
        Node<E> prev = lstNode;
        prev.item = value;
        lstNode = new Node(null, prev, null);
        prev.next = lstNode;
        size++;
        modCount++;
    }

    /**
     * Метод возвращает элемент по его индексу, используя цикл, в которм по ссылке next перебираются
     * элементы списка до искомого
     * @param index - место элемента в массиве.
     * @return элемент массива
     */
    public E get(int index) {
        Node<E> target = fstNode.next;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        return target.item;
    }

    /**
     * Класс Node содержит поле, в котором хранится элемент и два поля с ссылками на предыдущий
     * и следующий элементы списка
     */
    private class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Итератор для динамического списка на базе связанного списка
     * Зависит от того, где в коде создаем итератор-на том месте фиксирует число структурных изменений и при
     * изменении их числа генерирует ConcurrentModificationException
     */
    @Override
    public Iterator<E> iterator() {
        int expectedModCount = modCount;
        return new Iterator<E>() {

            int counter = 0;
            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return counter < size;
            }

            @Override
            public E next() {
                if (this.hasNext()) {
                    return get(counter++);
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
