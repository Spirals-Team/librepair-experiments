package spoon.support.util;


public class EmptyIterator<E> implements java.util.Iterator<E> {
    private static final spoon.support.util.EmptyIterator<java.lang.Object> EMPTY_ITERATOR = new spoon.support.util.EmptyIterator<>();

    public static <T> java.util.Iterator<T> instance() {
        return ((java.util.Iterator<T>) (spoon.support.util.EmptyIterator.EMPTY_ITERATOR));
    }

    EmptyIterator() {
    }

    @java.lang.Override
    public boolean hasNext() {
        return false;
    }

    @java.lang.Override
    public E next() {
        throw new java.util.NoSuchElementException();
    }

    @java.lang.Override
    public void remove() {
        throw new java.lang.IllegalStateException();
    }
}

