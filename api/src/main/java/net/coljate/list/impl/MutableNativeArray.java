package net.coljate.list.impl;

import java.util.NoSuchElementException;
import java.util.Objects;

import net.coljate.collection.Collection;
import net.coljate.list.AbstractArray;
import net.coljate.list.List;
import net.coljate.list.ListIterator;
import net.coljate.list.MutableArray;
import net.coljate.util.Arrays;

/**
 *
 * @author Ollie
 * @see java.util.ArrayList
 */
public class MutableNativeArray<T>
        extends AbstractArray<T>
        implements NativeArray<T>, MutableArray<T> {

    public static <T> MutableNativeArray<T> create(final int length) {
        return new MutableNativeArray<>(new Object[length], 0);
    }

    public static <T> MutableNativeArray<T> copyOf(final java.util.Collection<? extends T> collection) {
        return new MutableNativeArray<>(collection.toArray(), collection.size());
    }

    public static <T> MutableNativeArray<T> copyOf(final Collection<? extends T> collection) {
        return new MutableNativeArray<>(collection.arrayCopy(), collection.count());
    }

    public static <T> MutableNativeArray<T> copyOf(final T[] elements) {
        return new MutableNativeArray<>(Arrays.copyOf(elements), elements.length);
    }

    public static <T> MutableNativeArray<T> viewOf(final T[] elements) {
        return new MutableNativeArray<>(elements, elements.length);
    }

    private Object[] array;
    private int count;

    protected MutableNativeArray(final Object[] array, final int count) {
        this.array = array;
        this.count = count;
    }

    @Override
    public Object[] arrayCopy() {
        return Arrays.copyOf(array);
    }

    public boolean isOutOfBounds(final int index) {
        return index < 0 || index >= array.length;
    }

    @Override
    public T get(final int index) {
        return (T) array[index];
    }

    @Override
    public T set(int i, T value) {
        final T current = this.get(i);
        array[i] = value;
        return current;
    }

    @Override
    public int length() {
        return array.length;
    }

    @Override
    public ListIterator<T> iterator() {
        return new MutableNativeArrayForwardIterator();
    }

    @Override
    public ListIterator<T> reverseIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public void prefix(final T element) {
        final Object[] newArray = new Object[count + 1];
        newArray[0] = element;
        System.arraycopy(array, 0, newArray, 1, count);
        array = newArray;
        count++;
    }

    @Override
    public void suffix(final T element) {
        this.ensureCapacity(count + 1);
        array[count++] = element;
    }

    private void ensureCapacity(final int size) {
        if (array.length < size) {
            array = Arrays.copyOf(array, size);
        }
    }

    @Override
    public boolean removeFirst(final Object element) {
        for (int i = 0; i < count; i++) {
            if (Objects.equals(array[i], element)) {
                delete(i);
                return true;
            }
        }
        return false;
    }

    void delete(final int index) {
        System.arraycopy(array, index + 1, array, index, (--count) - index);
    }

    @Override
    public int removeAll(final Object element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(final Iterable<?> elements) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resize(int length) {
        array = new Object[length];
        count = Math.min(length, count);
    }

    @Override
    public void clear() {
        array = new Object[array.length];
        count = 0;
    }

    @Override
    public MutableNativeArray<T> mutableCopy() {
        return NativeArray.super.mutableCopy();
    }

    @Override
    public ImmutableNativeArray<T> immutableCopy() {
        return NativeArray.super.immutableCopy();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof MutableNativeArray
                && this.elementsEqual((List<?>) obj);
    }

    private final class MutableNativeArrayForwardIterator implements ListIterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < count;
        }

        @Override
        public T next() {
            if (isOutOfBounds(index)) {
                throw new NoSuchElementException();
            }
            return get(index++);
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public T previous() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void remove() {
            delete(index);
        }

    }

}
