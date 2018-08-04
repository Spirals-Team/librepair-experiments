package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
@ThreadSafe
public class DynamicArray<E> implements ISimpleContainer<E> {
    @GuardedBy("this")
    private Object[] container = new Object[1];
    @GuardedBy("this")
    private int modCount = 0;

    @Override
    public synchronized void add(E e) {
        if (!this.checkFreeCell()) {
            container = createBiggerArray();
        }
        container[container.length - 1] = e;
        modCount++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized E get(int index) {

        return (E) container[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new DynamicArrayIterator<>(this);
    }

    public synchronized int getModCount() {
        return modCount;
    }

    public synchronized Object[] getContainer() {
        return container;
    }

    private synchronized boolean checkFreeCell() {
        return container[container.length - 1] == null;
    }

    private synchronized Object[] createBiggerArray() {
        int lengthSrc = container.length;
        Object[] newContainer = new Object[lengthSrc + 1];
        System.arraycopy(container, 0, newContainer, 0, lengthSrc);
        return newContainer;
    }
}
