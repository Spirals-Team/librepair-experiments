package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
@ThreadSafe
public class DynamicArrayList<E> implements SimpleContainer<E> {
    @GuardedBy("this")
    private Object[] container = new Object[10];
    private int index = 0;

    @Override
    public synchronized void add(E value) {
        checkContainerSize();
        this.container[index++] = value;
    }

    @Override
    public synchronized E get(int i) {
        return (E) this.container[i];
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return new Iterator<E>() {
            private int iteratorIndex = 0;

            @Override
            public boolean hasNext() {
                synchronized (container) {
                    for (int i = iteratorIndex; i < container.length; i++) {
                        if (container[i] != null) {
                            iteratorIndex = i;
                            return true;
                        }
                    }
                    return false;
                }
            }

            @Override
            public E next() {
                synchronized (container) {
                    if (hasNext()) {
                        return (E) container[iteratorIndex++];
                    } else {
                        throw new NoSuchElementException("NoSuchElementException!");
                    }
                }
            }
        };
    }

    private synchronized void checkContainerSize() {
        if (this.index == this.container.length) {
            this.container = Arrays.copyOf(this.container, this.container.length * 2);
        }
    }
}
