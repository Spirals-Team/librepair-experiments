package net.coljate.list.impl;

import net.coljate.collection.impl.MutableWrappedCollection;
import net.coljate.list.ImmutableList;
import net.coljate.list.ListIterator;
import net.coljate.list.MutableList;

/**
 *
 * @author Ollie
 */
public class MutableWrappedList<T>
        extends MutableWrappedCollection<T>
        implements MutableList<T> {

    @SafeVarargs
    public static <T> MutableWrappedList<T> createArrayList(final T... elements) {
        final java.util.ArrayList<T> list = new java.util.ArrayList<>(elements.length);
        for (int i = 0; i < elements.length; i++) {
            list.add(elements[i]);
        }
        return new MutableWrappedList<>(list);
    }

    private final java.util.List<T> list;

    public MutableWrappedList(final java.util.List<T> delegate) {
        super(delegate);
        this.list = delegate;
    }

    @Override
    public java.util.List<T> mutableJavaCopy() {
        return new java.util.ArrayList<>(list);
    }

    @Override
    public void prefix(final T element) {
        list.add(0, element);
    }

    @Override
    public void suffix(final T element) {
        list.add(element);
    }

    @Override
    public T last() {
        return list.get(list.size() - 1);
    }

    @Override
    public ListIterator<T> iterator() {
        return new ForwardIterator();
    }

    @Override
    public ListIterator<T> reverseIterator() {
        return new ReverseIterator();
    }

    @Override
    public MutableList<T> mutableCopy() {
        return new MutableWrappedList<>(this.mutableJavaCopy());
    }

    @Override
    public ImmutableList<T> immutableCopy() {
        return MutableList.super.immutableCopy();
    }

    private final class ReverseIterator implements ListIterator<T> {

        private final java.util.ListIterator<T> iterator = list.listIterator(list.size() - 1);

        @Override
        public boolean hasPrevious() {
            return iterator.hasNext();
        }

        @Override
        public T previous() {
            return iterator.next();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasPrevious();
        }

        @Override
        public T next() {
            return iterator.previous();
        }

        @Override
        public void remove() {
            iterator.remove();
        }

    }

    private final class ForwardIterator implements ListIterator<T> {

        private final java.util.ListIterator<T> iterator = list.listIterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            return iterator.next();
        }

        @Override
        public boolean hasPrevious() {
            return iterator.hasPrevious();
        }

        @Override
        public T previous() {
            return iterator.previous();
        }

        @Override
        public void remove() {
            iterator.remove();
        }

    }

}
