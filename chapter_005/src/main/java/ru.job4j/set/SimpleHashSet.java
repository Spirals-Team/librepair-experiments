package ru.job4j.set;

import java.util.Iterator;
import java.util.NoSuchElementException;

//Напишите свою реализацию Set на базе хэш-таблицы. Реализуйте следующие методы:
//        1) boolean add (E e)
//        2) boolean contains (E e)
//        3) boolean remove (E e)
//
//        Принципы организации хэш-таблиц вы сможете узнать из следующих источников:
//        1) Wikipedia - хеш-таблица
//        2) Core Java - К.Хорстманн
//        3) Алгоритмы. Построение и анализ. - Т.Кормен и др.
//        4) Алгоритмы и структуры данных. - Н. Вирт
//
//        Разрешение коллизий реализовывать не надо.
public class SimpleHashSet<T> implements Iterable<T>  {

    Node<T> firstInList;
    int sizeOfList = 0;
    Node<T> lastInList;

    private final static int TABLE_SIZE = 128;

    Node<T>[] tableHashTable;

    public SimpleHashSet() {
        tableHashTable = new Node[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            tableHashTable[i] = null;
        }

    }

    public int getSize() {
        return sizeOfList;
    }

    //1) boolean add (E e)
    public boolean add(T value) {

        if (!suchAnElementAlreadyExists(value)) {

            Node<T> newNode = new Node<T>(value);
            if (this.firstInList == null) {
                this.firstInList = newNode;
                this.lastInList = newNode;
            } else {
                this.lastInList.next = newNode;
                this.lastInList = newNode;
            }
            sizeOfList++;

            int hash = getHashByValue(value);

            tableHashTable[hash] = newNode;

            return true;

        }

        return false;
    }

//    2) boolean contains (E e)
    public  boolean contains(T value) {

        int hash = getHashByValue(value);

        return  !(tableHashTable[hash] == null);

    }

//    3) boolean remove (E e)
    public boolean remove(T value) {

        boolean returnValue = false;

        if (!this.contains(value)) {
            returnValue = false;
        } else  {

            int hash = getHashByValue(value);

            Node<T> nodeForRemove = tableHashTable[hash];

            if (nodeUnlink(nodeForRemove)) {

                tableHashTable[hash] = null;

                returnValue = true;
            }

        }

        return returnValue;
    }

    public boolean nodeUnlink(Node<T> node) {

        Node<T> nextNode = node.next;

        Node<T> prevNode = findPreviosNode(node);

        if (prevNode != null) {
            prevNode.next = nextNode;

            node.next = null;
            node.value = null;

            this.sizeOfList--;

            return true;
        } else {
            return false;
        }
    }

    public Node<T> findPreviosNode(Node<T> node) {

        Node<T> currNode = firstInList;

        while (currNode != null && currNode.next != firstInList) {
            if (currNode.next.equals(node)) {
                return currNode;
            }
        }

        return null;

    }

    int getHashByValue(T value) {

        int hash = (value.hashCode() % TABLE_SIZE);

        while (tableHashTable[hash] != null && tableHashTable[hash].value.hashCode() % TABLE_SIZE != hash) {

            hash = (hash + 1) % TABLE_SIZE;
        }

        return hash;

    }


//  WORK ON BUGS    смысл кеш структур убил.if (!suchAnElementAlreadyExists(value)) { ты делаешь поиск по всем элементам. а тут надо просто вычислить ячейку и проверить пустая она или нет.
    boolean suchAnElementAlreadyExists(T t) {

//        boolean returnValue = false;

//        Node<T> currNode = firstInList;
//
//        while (currNode != null && currNode.next != firstInList) {
//
//            if (currNode.value.equals(t)) {
//
//                returnValue = true;
//
//                break;
//
//            } else {
//
//                currNode = currNode.next;
//            }
//        }

//        int hash = getHashByValue(t);

//        return returnValue;

        return tableHashTable[getHashByValue(t)] != null;

    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    class Itr implements Iterator<T> {

        Node<T> itrLastNode;

        int itrIndex = 0;

        @Override
        public boolean hasNext() {
            return itrIndex < sizeOfList;
        }


        @Override
        public T next() {

            T returnValue = null;

            if (!hasNext()) {

                new Exception("no such element!!! ");

            } else {

                itrLastNode = itrLastNode == null ? firstInList : itrLastNode.next;
                returnValue = itrLastNode.value;
            }

            itrIndex++;

            return returnValue;
        }
    }

    public class Node<T> {
        T value;

        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }


}