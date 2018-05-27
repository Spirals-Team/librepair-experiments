package ru.job4j.set;

import ru.job4j.list.ArrayContainer;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class SimpleSet<T> extends ArrayContainer<T> {
    @Override
    public synchronized void add(T o) {
        if (!super.contains(o)) {
            super.add(o);
        }
    }

    @Override
    public synchronized T get(int index) {
        return super.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return super.iterator();
    }

    @Override
    public synchronized int getSize() {
        return super.getSize();
    }

    @Override
    public synchronized boolean contains(T t) {
        return super.contains(t);
    }
}
