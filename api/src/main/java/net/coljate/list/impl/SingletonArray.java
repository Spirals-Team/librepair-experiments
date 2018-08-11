package net.coljate.list.impl;

import java.io.Serializable;
import java.util.NoSuchElementException;

import net.coljate.list.AbstractArray;
import net.coljate.list.ImmutableArray;
import net.coljate.list.ImmutableListIterator;

/**
 *
 * @author Ollie
 */
public class SingletonArray<T>
        extends AbstractArray<T>
        implements ImmutableArray<T>, Serializable {

    private static final long serialVersionUID = 1L;

    public static <T> SingletonArray<T> of(final T element) {
        return new SingletonArray<>(element);
    }

    private final T element;

    protected SingletonArray(final T element) {
        this.element = element;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public T get(final int index) {
        if (index != 0) {
            throw new IndexOutOfBoundsException("Singleton collection with index = " + index + " != 0");
        }
        return element;
    }

    @Override
    public T first() {
        return element;
    }

    @Override
    public T last() {
        return element;
    }

    @Override
    public java.util.ArrayList<T> mutableJavaCopy() {
        final java.util.ArrayList<T> list = new java.util.ArrayList<>(1);
        list.add(element);
        return list;
    }

    @Override
    public ImmutableListIterator<T> iterator() {
        return new ImmutableListIterator<T>() {

            private boolean exhausted;

            @Override
            public boolean hasPrevious() {
                return exhausted;
            }

            @Override
            public T previous() {
                if (!exhausted) {
                    throw new NoSuchElementException();
                }
                exhausted = false;
                return element;
            }

            @Override
            public boolean hasNext() {
                return !exhausted;
            }

            @Override
            public T next() {
                if (exhausted) {
                    throw new NoSuchElementException();
                }
                exhausted = true;
                return element;
            }

        };

    }

}
