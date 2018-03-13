package ru.job4j.set;

import java.util.Iterator;

//Реализовать коллекцию SimpleSet.
//        Коллекция должна обеспечивать void add(E e) и реализовывать Iterator<E>.
//        Коллекция не должна хранить дубликаты.
//        Set - внутри для хранения данных использует связный список.

//WORK ON BUGS
//аналогично java.util.Iterator.SimpleSet
//        1) suchAnElementAlreadyExists  - упростите
//        2) метод next можно написать проще

public class SimpleLinkedSet<T> implements Iterable<T> {

    Node<T> firstInList;
    int sizeOfList = 0;
    Node<T> lastInList;

    public int getSize() {
        return sizeOfList;
    }

    public void add(T value) {

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

        }
    }

//    boolean suchAnElementAlreadyExists(T t) {
//
//        boolean returnValue = false;
//
//        Iterator<T> it = this.iterator();
//
//        while (it.hasNext()) {
//
//            T currElement = it.next();
//
//            if (currElement.equals(t)) {
//
//                returnValue = true;
//
//                break;
//
//            }
//        }
//        return returnValue;
//    }

    boolean suchAnElementAlreadyExists(T t) {

        boolean returnValue = false;

        Node<T> currNode = firstInList;

        while (currNode != null && currNode.next != firstInList) {

            if (currNode.value.equals(t)) {

                returnValue = true;

                break;

            } else {

                currNode = currNode.next;
            }
        }
        return returnValue;
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


//        @Override
//        public T next() {
//
//            T returnValue = null;
//
//            if (!hasNext()) {
//                new Exception("no such element!!! ");
//            } else {
//
//                if (itrLastNode == null) {
//                    returnValue = firstInList.value;
//                    itrLastNode = firstInList;
//
//                } else {
//                    Node<T> nextNode = itrLastNode.next;
//                    returnValue = nextNode.value;
//                    itrLastNode = nextNode;
//
//                }
//            }
//
//            itrIndex++;
//            return returnValue;
//        }

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
