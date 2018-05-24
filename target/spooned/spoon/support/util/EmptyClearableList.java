package spoon.support.util;


public final class EmptyClearableList<E> extends java.util.AbstractList<E> implements java.io.Serializable , java.util.RandomAccess {
    private static final long serialVersionUID = 0L;

    private static final spoon.support.util.EmptyClearableList<java.lang.Object> EMPTY_LIST = new spoon.support.util.EmptyClearableList<>();

    public static <T> java.util.List<T> instance() {
        return ((java.util.List<T>) (spoon.support.util.EmptyClearableList.EMPTY_LIST));
    }

    private EmptyClearableList() {
    }

    @java.lang.Override
    public void clear() {
    }

    @java.lang.Override
    public java.util.Iterator<E> iterator() {
        return spoon.support.util.EmptyIterator.instance();
    }

    @java.lang.Override
    public java.util.ListIterator<E> listIterator() {
        return ((java.util.ListIterator<E>) (spoon.support.util.EmptyClearableList.EmptyListIterator.EMPTY_LIST_ITERATOR));
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

    @java.lang.Override
    public E get(int index) {
        throw new java.lang.IndexOutOfBoundsException(("Index: " + index));
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        return (o instanceof java.util.List) && (((java.util.List<?>) (o)).isEmpty());
    }

    @java.lang.Override
    public int hashCode() {
        return 1;
    }

    private java.lang.Object readResolve() {
        return spoon.support.util.EmptyClearableList.EMPTY_LIST;
    }

    private static final class EmptyListIterator<E> extends spoon.support.util.EmptyIterator<E> implements java.util.ListIterator<E> {
        static final spoon.support.util.EmptyClearableList.EmptyListIterator<java.lang.Object> EMPTY_LIST_ITERATOR = new spoon.support.util.EmptyClearableList.EmptyListIterator<>();

        @java.lang.Override
        public boolean hasPrevious() {
            return false;
        }

        @java.lang.Override
        public E previous() {
            throw new java.util.NoSuchElementException();
        }

        @java.lang.Override
        public int nextIndex() {
            return 0;
        }

        @java.lang.Override
        public int previousIndex() {
            return -1;
        }

        @java.lang.Override
        public void set(E e) {
            throw new java.lang.IllegalStateException();
        }

        @java.lang.Override
        public void add(E e) {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}

