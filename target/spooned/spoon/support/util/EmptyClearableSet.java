package spoon.support.util;


public final class EmptyClearableSet<E> extends java.util.AbstractSet<E> implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    private static final spoon.support.util.EmptyClearableSet<java.lang.Object> EMPTY_SET = new spoon.support.util.EmptyClearableSet<>();

    public static <T> java.util.Set<T> instance() {
        return ((java.util.Set<T>) (spoon.support.util.EmptyClearableSet.EMPTY_SET));
    }

    private EmptyClearableSet() {
    }

    @java.lang.Override
    public void clear() {
    }

    @java.lang.Override
    public java.util.Iterator<E> iterator() {
        return spoon.support.util.EmptyIterator.instance();
    }

    @java.lang.Override
    public int size() {
        return 0;
    }

    @java.lang.Override
    public boolean isEmpty() {
        return true;
    }

    @java.lang.Override
    public boolean contains(java.lang.Object obj) {
        return false;
    }

    @java.lang.Override
    public boolean containsAll(java.util.Collection<?> c) {
        return c.isEmpty();
    }

    @java.lang.Override
    public java.lang.Object[] toArray() {
        return new java.lang.Object[0];
    }

    @java.lang.Override
    public <T> T[] toArray(T[] a) {
        if ((a.length) > 0) {
            a[0] = null;
        }
        return a;
    }

    private java.lang.Object readResolve() {
        return spoon.support.util.EmptyClearableSet.EMPTY_SET;
    }
}

