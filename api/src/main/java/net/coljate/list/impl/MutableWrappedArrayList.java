package net.coljate.list.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

import net.coljate.collection.Collection;
import net.coljate.list.MutableArray;

/**
 *
 * @author Ollie
 */
public class MutableWrappedArrayList<T>
        extends MutableWrappedList<T>
        implements MutableArray<T>, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Field arrayListCapacity;

    static {
        try {
            arrayListCapacity = ArrayList.class.getDeclaredField("elementData");
            arrayListCapacity.setAccessible(true);
        } catch (final NoSuchFieldException | SecurityException ex) {
            throw new Error("Could not read ArrayList capacity", ex);
        }
    }

    public static <T> MutableWrappedArrayList<T> create(final int size) {
        return viewOf(new java.util.ArrayList<>(size));
    }

    public static <T> MutableWrappedArrayList<T> viewOf(final java.util.ArrayList<T> list) {
        return new MutableWrappedArrayList<>(list);
    }

    @SafeVarargs
    public static <T> MutableWrappedArrayList<T> copyOf(final T... elements) {
        final java.util.ArrayList<T> list = new java.util.ArrayList<>(elements.length);
        for (int i = 0; i < elements.length; i++) {
            list.add(elements[i]);
        }
        return new MutableWrappedArrayList<>(list, elements.length);
    }

    public static <T> MutableWrappedArrayList<T> copyOf(final Collection<? extends T> collection) {
        final java.util.ArrayList<T> list = collection.mutableJavaCopy(java.util.ArrayList::new);
        return viewOf(list);
    }

    private final java.util.ArrayList<T> delegate;
    private final boolean lengthManaged;
    private int length;

    protected MutableWrappedArrayList(final java.util.ArrayList<T> delegate, final int length) {
        super(delegate);
        this.delegate = delegate;
        this.length = length;
        this.lengthManaged = true;
    }

    protected MutableWrappedArrayList(final java.util.ArrayList<T> delegate) {
        super(delegate);
        this.delegate = delegate;
        this.lengthManaged = false;
    }

    @Override
    public java.util.ArrayList<T> mutableJavaCopy() {
        return this.mutableJavaCopy(java.util.ArrayList::new);
    }

    @Override
    public T get(final int index) {
        return index >= this.count()
                ? null
                : delegate.get(index);
    }

    @Override
    public T set(final int i, final T value) {
        return delegate.set(i, value);
    }

    @Override
    public int count() {
        return delegate.size();
    }

    @Override
    public int length() {
        return lengthManaged
                ? length
                : capacity(delegate);
    }

    @SuppressWarnings("dereference.of.nullable")
    private static int capacity(final ArrayList<?> list) {
        try {
            return ((Object[]) arrayListCapacity.get(list)).length;
        } catch (final IllegalAccessException iex) {
            throw new Error(iex);
        }
    }

    @Override
    public void resize(final int length) {
        this.length = length;
        delegate.ensureCapacity(length);
    }

    @Override
    public MutableArray<T> mutableCopy() {
        return this.lengthManaged
                ? new MutableWrappedArrayList<>(this.mutableJavaCopy(), length)
                : new MutableWrappedArrayList<>(this.mutableJavaCopy());
    }

    @Override
    public ImmutableNativeArray<T> immutableCopy() {
        final Object[] array = delegate.toArray();
        return new ImmutableNativeArray<>(array, delegate.size());
    }

}
