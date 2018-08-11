package net.coljate.list.impl;

import net.coljate.collection.Collection;
import net.coljate.list.AbstractArray;
import net.coljate.list.Array;
import net.coljate.list.ImmutableArray;
import net.coljate.list.ImmutableListIterator;
import net.coljate.util.Arrays;

/**
 *
 * @author Ollie
 */
public class ImmutableNativeArray<T>
        extends AbstractArray<T>
        implements NativeArray<T>, ImmutableArray<T> {

    public static <T> ImmutableNativeArray<T> copyOf(final Collection<? extends T> collection) {
        return new ImmutableNativeArray<>(collection.arrayCopy(), collection.count());
    }

    public static <T> ImmutableNativeArray<T> copyOf(final T[] array) {
        return new ImmutableNativeArray<>(Arrays.copyOf(array), array.length);
    }

    public static <T> ImmutableNativeArray<T> reverseCopy(final Array<? extends T> array) {
        final Object[] copy = array.arrayCopy();
        Arrays.reverseInPlace(copy);
        return new ImmutableNativeArray<>(copy, array.count()); //FIXME
    }

    private final Object[] array;
    private final int count;

    protected ImmutableNativeArray(final Object[] array) {
        this(array, array.length);
    }

    protected ImmutableNativeArray(final Object[] array, final int count) {
        this.array = array;
        this.count = count;
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public Object[] arrayCopy() {
        return Arrays.copyOf(array, count);
    }

    @Override
    public int length() {
        return array.length;
    }

    @Override
    public T get(final int index) {
        if (index >= count) {
            throw new IndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    @Override
    public ImmutableListIterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    @Deprecated
    public ImmutableNativeArray<T> immutableCopy() {
        return this;
    }

    @Override
    public MutableNativeArray<T> mutableCopy() {
        return NativeArray.super.mutableCopy();
    }

    private final class ArrayIterator implements ImmutableListIterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < count;
        }

        @Override
        public T next() {
            return get(index++);
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public T previous() {
            return get(--index);
        }

    }

}
