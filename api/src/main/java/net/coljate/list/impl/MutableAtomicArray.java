package net.coljate.list.impl;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Function;

import net.coljate.list.AbstractList;
import net.coljate.list.ConcurrentArray;
import net.coljate.list.ImmutableArray;
import net.coljate.list.ListIterator;

/**
 *
 * @author Ollie
 */
public class MutableAtomicArray<T>
        extends AbstractList<T>
        implements ConcurrentArray<T> {

    public static <T> MutableAtomicArray<T> create(final int length) {
        return new MutableAtomicArray<>(new AtomicReferenceArray<>(length));
    }

    public static <T> MutableAtomicArray<T> copyOf(final java.util.Collection<? extends T> collection) {
        @SuppressWarnings("unchecked")
        final AtomicReferenceArray<T> array = new AtomicReferenceArray<>((T[]) collection.toArray());
        return new MutableAtomicArray<>(array);
    }

    @SafeVarargs
    public static <T> MutableAtomicArray<T> copyOf(final T... elements) {
        final AtomicReferenceArray<T> array = new AtomicReferenceArray<>(elements);
        return new MutableAtomicArray<>(array);
    }

    private final AtomicReference<AtomicReferenceArray<T>> arrayRef;

    protected MutableAtomicArray(final AtomicReferenceArray<T> array) {
        this.arrayRef = new AtomicReference<>(array);
    }

    private AtomicReferenceArray<T> array() {
        return arrayRef.get();
    }

    private static <T> AtomicReferenceArray<T> copy(final AtomicReferenceArray<T> current) {
        return copy(current, current.length());
    }

    private static <T> AtomicReferenceArray<T> copy(final AtomicReferenceArray<T> source, final int length) {
        return copy(source, 0, 0, length);
    }

    /**
     * @see System#arraycopy(java.lang.Object, int, java.lang.Object, int, int)
     */
    private static <T> AtomicReferenceArray<T> copy(
            final AtomicReferenceArray<T> source,
            final int sourcePosition,
            final int destinationPosition,
            final int length) {
        final AtomicReferenceArray<T> copy = new AtomicReferenceArray<>(length);
        for (int i = 0; i < source.length(); i++) {
            copy.set(i + destinationPosition, source.get(i + sourcePosition));
        }
        return copy;
    }

    private AtomicReferenceArray<T> spinReplace(final Function<AtomicReferenceArray<T>, AtomicReferenceArray<T>> replace) {
        AtomicReferenceArray<T> current, next;
        do {
            current = this.array();
            next = replace.apply(current);
        } while (!arrayRef.compareAndSet(current, next));
        return next;
    }

    @Override
    public T get(final int index) {
        return this.array().get(index);
    }

    @Override
    public T set(int i, T value) {
        return this.array().getAndSet(i, value);
    }

    @Override
    public boolean replace(int index, T expectedValue, T replacementValue) {
        return this.array().compareAndSet(index, expectedValue, replacementValue);
    }

    @Override
    public int length() {
        return this.array().length();
    }

    @Override
    public ImmutableArray<T> immutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ListIterator<T> iterator() {
        return new AtomicArrayIterator<>(this.array());
    }

    @Override
    public ListIterator<T> reverseIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void prefix(final T element) {
        this.spinReplace(current -> prefix(element, current));
    }

    private static <T> AtomicReferenceArray<T> prefix(final T element, final AtomicReferenceArray<T> array) {
        final AtomicReferenceArray<T> expanded = copy(array, 0, 1, array.length() + 1);
        expanded.set(expanded.length() - 1, element);
        return expanded;
    }

    @Override
    public void suffix(final T element) {
        this.spinReplace(current -> suffix(current, element));
    }

    private static <T> AtomicReferenceArray<T> suffix(final AtomicReferenceArray<T> array, final T element) {
        final AtomicReferenceArray<T> expanded = copy(array, array.length() + 1);
        expanded.set(0, element);
        return expanded;
    }

    @Override
    public void resize(final int size) {
        this.spinReplace(current -> copy(current, size));
    }

    @Override
    public void clear() {
        this.spinReplace(current -> new AtomicReferenceArray<>(current.length()));
    }

    @Override
    public ConcurrentArray<T> mutableCopy() {
        return new MutableAtomicArray<>(copy(this.array()));
    }

    private static final class AtomicArrayIterator<T> implements ListIterator<T> {

        private final AtomicReferenceArray<T> array;
        private int index;

        AtomicArrayIterator(final AtomicReferenceArray<T> array) {
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return index < array.length();
        }

        @Override
        public T next() {
            return array.get(index++);
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public T previous() {
            return array.get(--index);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(); //TODO
        }

    }

}
