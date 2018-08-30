package ru.job4j.set;

import ru.job4j.list.DynamicList;

import java.util.Iterator;

public class SimpleSet<E> {

    private DynamicList<E> container;

    public SimpleSet() {
        this.container = new DynamicList<>();
    }

    public SimpleSet(E... args) {
        this.container = new DynamicList<>(args.length);
        for (int idx = 0; idx < container.length(); idx++) {
            add(args[idx]);
        }
    }

    public void add(E value) {
        for (int idx = 0; idx < container.length(); idx++) {
            if (isValuePresent(idx) && container.get(idx).equals(value)) {
                return;
            }
        }
        container.add(value);
    }

    public Iterator<E> iterator() {
        Iterator it = container.iterator();
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public E next() {
                return (E) it.next();
            }
        };
    }
    
    @Override
    public String toString() {
        String result = "";
        for (int idx = 0; idx < container.length(); idx++) {
            if (container.get(idx) != null) {
                result += container.get(idx);
            }
        }
        return result;
    }

    private boolean isValuePresent(int idx) {
        return container.get(idx) != null;
    }
}