package ru.job4j.set;



import ru.job4j.container.Dynamic;

import java.util.Iterator;

public class SimpleSet<E> implements Iterable<E> {
    private transient Dynamic<E> list = new Dynamic<>();

    public int size() {
        return list.size();
    }


    public boolean add(E e) {
        boolean result = true;
        for (int index = 0; index < this.list.size(); index++) {
            if (e == null ? this.list.get(index) == null : e.equals(this.list.get(index))) {
                result = false;
                break;
            }
        }
        if (result) {
            this.list.add(e);
        }
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
}
