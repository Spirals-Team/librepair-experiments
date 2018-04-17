package ru.job4j.set;

import ru.job4j.list.DynamicArray;
import ru.job4j.list.DynamicArrayIterator;
import ru.job4j.list.NodeList;
import ru.job4j.list.NodeListIterator;

import java.util.Iterator;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SimpleSetNode<E> implements Iterable<E> {
    private NodeList<E> nodeList = new NodeList<>();

    @Override
    public Iterator<E> iterator() {
        return new NodeListIterator<>(nodeList);
    }

    public void add(E e) {
        if (!contains(e)) {
            nodeList.add(e);
        }
    }

    public boolean contains(E e) {
        boolean result = false;
        for (E item : nodeList) {
            if (e.equals(item)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
