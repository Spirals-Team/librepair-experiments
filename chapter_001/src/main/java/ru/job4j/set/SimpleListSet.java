package ru.job4j.set;

import ru.job4j.List.LinkList;
import java.util.Iterator;

/**
 * Класс представляет собой множество типа E реализованное через связанный список
 * @param <E> необходимый тип данных
 */
public class SimpleListSet<E> implements Iterable<E> {
    LinkList<E> inner = new LinkList<>();

    public int size() {
        return inner.size();
    }

    public void add(E value) {
        boolean result = false;
        for (E obj: inner) {
            if (obj.equals(value)) {
                result = true;
                break;
            }
        }
        if (! result) {
            inner.add(value);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return this.inner.iterator();
    }
}
