package ru.job4j.set;

import ru.job4j.list.ListContainer;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class SimpleSetList<T> extends ListContainer<T> {
    @Override
    public synchronized void add(T o) {
        for (int i = 0; i < super.getSize(); i++) {
            if (super.get(i).equals(o)) {
                return;
            }
        }
        super.add(o);
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
    public synchronized void remove(int index) {
        super.remove(index);
    }

    @Override
    public synchronized void see() {
        super.see();
    }
}
