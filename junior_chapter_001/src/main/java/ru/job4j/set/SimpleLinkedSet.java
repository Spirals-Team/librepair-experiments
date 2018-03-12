package ru.job4j.set;

import ru.job4j.list.DynamicLinkedList;

import java.util.Iterator;

public class SimpleLinkedSet<T> implements Iterable<T> {
    DynamicLinkedList<T> dll;

    public SimpleLinkedSet() {
        this.dll = new DynamicLinkedList<>();
    }

    public void add(T e) {
        if (!checkForDuplicates(e)) {
            dll.add(e);
        }
    }

    public boolean checkForDuplicates(T e) {
        Iterator<T> iterator = dll.iterator();
        while (iterator.hasNext()) {
            if (e == iterator.next()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return dll.iterator();
    }
}
