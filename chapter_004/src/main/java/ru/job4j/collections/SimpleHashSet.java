package ru.job4j.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashSet<E> implements Iterable<E> {

    private Node<E>[] hashTable;
    public int size = 0;
    private float threshold;

    public SimpleHashSet() {
        hashTable = new Node[16];
        threshold = hashTable.length * 0.75f;
    }

    /**
     * Метод добавляет элемент в хэштаблицу. Если элемент с таким хэшем уже содержится в таблице,
     * добавления не происходит и возвращает false, если такого хэша еще нет в массиве, то
     * увеличивает размер массива и добавляем элемент на место с его хэшем.
     * @param e - добавляемый элемент.
     * @return true если такого элемента еще не было в хэштаблице.
     */
    public boolean add(E e) {
        boolean result = false;
        if (!this.contains(e)) {
            if (size + 1 >= threshold) {
                threshold *= 2;
                arrayDoubling();
            }
            Node<E> newNode = new Node<>(e);
            int index = newNode.hash;
            hashTable[index] = newNode;
            size++;
            result = true;
        }
        return result;
    }

    /**
     * Вспомогательный метод для увеличения размера хэштаблицы мержем.
     */
    private void arrayDoubling() {
        Node<E>[] oldHashTable = hashTable;
        hashTable = new Node[oldHashTable.length * 2];
        size = 0;
        for (Node<E> node : oldHashTable) {
            if (node != null) {
                add(node.value);
            }
        }
    }

    /**
     * Метод проверяет содержится ли элемент в хэштаблице. Проверка осущесвтляется путем вычисления хэша элемента
     * и сверки элемента находящегося по этому хэшу. если там содержится элемент, то возвращает true
     * @param e- проверяемый элемент.
     * @return false, если по вычисленному хэшу элемента нет никакого элемента
     */
    public boolean contains(E e) {
        boolean result = false;
        Node<E> newNode = new Node<>(e);
        int index = newNode.hash;
        if (hashTable[index] != null) {
            result = true;
        }
        return result;
    }

    /**
     * Метод удаляет элемент из хэштаблицы. Удаление происходит путем вычисления хэша элемента,
     * если с таким хэшем в массиве есть элемнт, то зануляем его, если нет, то возврашаем false.
     * @param e- проверяемый элемент.
     * @return false, если по вычисленному хэшу элемента нет никакого элемента
     */
    public boolean remove(E e) {
        Node<E> newNode = new Node<>(e);
        int index = newNode.hash;
        if (hashTable[index] == null) {
            return false;
        } else {
            hashTable[index] = null;
            size--;
            return true;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                boolean res = false;
                for (int i = count; i < hashTable.length; i++) {
                    if (hashTable[i] != null) {
                        res = true;
                        count = i;
                        break;
                    }
                }
                return res;
            }

            @Override
            public E next() {
                if (this.hasNext()) {
                    E res = hashTable[count].value;
                    count++;
                    return res;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    private class Node<E> {
        private int hash;
        private E value;

        private Node(E value) {
            this.value = value;
            this.hash = hash(value);
        }

        private int hash(E value) {
            int hash = 31;
            hash = hash * 17 + hashCode();
            return hash % hashTable.length;
        }

        @Override
        public int hashCode() {
            hash = 31;
            hash = hash * 17 + value.hashCode();
            return hash;

        }
    }
}
