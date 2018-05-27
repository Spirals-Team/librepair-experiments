package ru.job4j.generic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleArray<T> implements Iterable<T> {
    private ArrayList<T> simpleList = new ArrayList<>();

    public void add(T model) {
        simpleList.add(model);
    }

    public void set(int index, T model) {
        simpleList.set(index, model);
    }

    public void delete(int index) {
        simpleList.remove(index);
    }

    public T get(int index) {
        return simpleList.get(index);
    }

    public int size() {
        return simpleList.size();
    }

    @Override
    public Iterator<T> iterator() {
        return simpleList.iterator();
    }
}
