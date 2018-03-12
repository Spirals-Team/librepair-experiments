package ru.job4j.set;

import ru.job4j.list.DynamicArrayList;

import java.util.Iterator;

public class SimpleArraySet<T> implements Iterable<T> {
    DynamicArrayList<T> ts;

    public SimpleArraySet() {
        this.ts = new DynamicArrayList<>();
    }

    public void add(T e) {
        if (!checkForDuplicates(e)) {
            ts.add(e);
        }
    }

    public boolean checkForDuplicates(T e) {
        Iterator<T> iterator = ts.iterator();
        while (iterator.hasNext()) {
            if (e == iterator.next()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return ts.iterator();
    }
}