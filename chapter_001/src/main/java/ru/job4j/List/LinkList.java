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
public class LinkList<E> implements Iterable<E>  {
    @GuardedBy("this")
    // ссылка на первый узел списка
    private Node<E> firstNode;
    // ссылка на последний узел списка
    private Node<E> lastNode;
    // размер списка
    private int size = 0;
    // счетчик изменений контейнера
    private int modCount = 0;

    /**
     * Конструктор
     */
    public LinkList() {
    }

    /**
     * Класс узла связанного списка
     * Модификатор default выбран т к данный класс испольхуется в других структурах пакета
     * @param <E> тип
     */
    static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node(E model) {
            data = model;
        }
    }
    /**
     * Метод подсчета текущего размера хранилища данных
     * @return размер
     */
    public int size() {
        return size;
    }

    public Node<E> getFirstNode() {
        return firstNode;
    }

    public synchronized void setFirstNode(Node<E> firstNode) {
        this.firstNode = firstNode;
    }

    public Node<E> getLastNode() {
        return lastNode;
    }

    public synchronized void setLastNode(Node<E> lastNode) {
        this.lastNode = lastNode;
    }

    public synchronized void setSize(int size) {
        this.size = size;
    }

    /**
     * Метод добавляет объект в хранилище (синхронизрован)
     * @param model объект для добавления
     */
    public synchronized void add(E model) {
        if (size == 0) {
            this.firstNode = new Node<E>(model);
            this.lastNode = this.firstNode;
        } else {
            this.lastNode.right = new Node<E> (model);
            this.lastNode.right.left = this.lastNode;
            this.lastNode = this.lastNode.right;
        }
        size++;
        modCount++;
    }

    /**
     * Метод получает объект в хранилище по нужному индексу
     * В случаи отсутствия нужного индекса выдает ошибку
     * @param index индекс хранения
     * @return объект типа E
     */
    public E get(int index) {
        if (index < size) {
            int i = 0;
            Iterator<E> iter = this.iterator();
            while (i++ < index) {
                iter.next();
            }
            return iter.next();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Метод удаляет объект из хранилища по значению (синхронизирован)
     * @param value значение
     * @return удалось ли удаление
     */
    public synchronized boolean remove(E value) {
        boolean result = false;
        Node<E> current = this.firstNode;
        Iterator<E> iter = this.iterator();
        while (iter.hasNext()) {
            if (current.data.equals(value)) {
                if (current.left != null) {
                    current.left.right = current.right;
                }
                if (current.right != null) {
                    current.right.left = current.left;
                }
                result = true;
                size--;
                modCount++;
                break;
            }
            iter.next();
            current = current.right;
        }
        return result;
    }

    @Override
    /**
     *  Метод создает итератор для контейнера
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int pointer = 0;
            Node<E> pointNode = firstNode;
            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return pointer < size;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    E result = pointNode.data;
                    pointer++;
                    pointNode = pointNode.right;
                    return result;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
