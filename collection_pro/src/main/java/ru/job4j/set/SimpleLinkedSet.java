package ru.job4j.set;

import ru.job4j.list.FirstLastList;

import java.util.Iterator;

public class SimpleLinkedSet<E> implements Iterable<E> {

    private transient FirstLastList<E> list = new FirstLastList<>();

    public int size() {
        return list.getSize();
    }
    public boolean checkOfDuplicates(E t) {
        boolean result = false;
        Iterator<E> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(t)) {
                result = true;
            }
        }
        return result;
    }

    public boolean isEmpty() {
       return list.isEmpty();
    }

    public boolean add(E e) {
        for (E check: this.list) {
            if (check.equals(e)) {
                return false;
            }
        }
        this.list.add(e);
        return true;
}



    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
}
