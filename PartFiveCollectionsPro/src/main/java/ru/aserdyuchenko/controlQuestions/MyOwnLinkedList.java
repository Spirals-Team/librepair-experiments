package ru.aserdyuchenko.controlQuestions;

import ru.aserdyuchenko.list.task_5_3_1.SimpleContainer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * @author anton.
 * My own linked list.
 * @param <E> - E.
 */
public class MyOwnLinkedList<E> implements SimpleContainer<E> {
    /**
     * @param head - head.
     */
    private Node head;
    /**
     * @param current - current.
     */
    private Node current;

    @Override
    public void add(E e) {
        if (head == null) {
            head = new Node(e);
        } else {
            Node temp = new Node(e);
            current = head;

            if (current != null) {
                while (current.getNext() != null) {
                    current = current.getNext();
                }
                current.setNext(temp);
            }
        }
    }

    @Override
    public E get(int index) {
        if (head != null) {
            current = head.getNext();
            for (int i = 0; i < index; i++) {
                if (current.getNext() == null) {
                    return null;
                }
                current = current.getNext();
            }
            return (E) current.getData();
        }
        return (E) current;
    }

    public void mirror() {
        Node node = head.getNext();
        head.setNext(null);
        while (node != null) {
            Node temp = node.getNext();
            node.setNext(head);
            head = node;
            node = temp;
        }
    }

    public E[] toArray() {
        List<E> list = new ArrayList<>();
        Node node = head;
        while (node != null) {
            list.add((E) node.getData());
            node = node.getNext();
        }
        return (E[]) list.toArray();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Stack<E> stack = new Stack<>();
            private int index = 0;

            @Override
            public boolean hasNext() {
                return (!stack.isEmpty() || current != null);
            }

            private void popInStack(Node<E> node) {
                stack.add(0, node.getData());
                if (node.getNext() != null) {
                    popInStack(node.getNext());
                }
                current = null;
            }

            @Override
            public E next() {
                if (index == 0) {
                    current = head;
                    popInStack(current);
                }
                index++;
                return stack.pop();
            }
        };
    }

}

/**
 * Node.
 */
class Node<E> {
    /**
     * @param next - next.
     */
    private Node next;
    /**
     * @param data - data.
     */
    private E data;

    /**
     * Constructor.
     * @param dataVale - data value.
     */
    Node(E dataVale) {
        next = null;
        data = dataVale;
    }

    /**
     * Get data.
     * @return - data.
     */
    public E getData() {
        return  data;
    }

    /**
     * Get next.
     * @return - next.
     */
    public Node getNext()  {
        return next;
    }

    /**
     * Set next.
     * @param nextValue - next value.
     */
    public void setNext(Node nextValue) {
        next = nextValue;
    }
}
