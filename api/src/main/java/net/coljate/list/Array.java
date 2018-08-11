package net.coljate.list;

import java.util.function.Consumer;

import net.coljate.collection.Collection;
import net.coljate.list.impl.ArrayIterator;
import net.coljate.list.impl.ImmutableNativeArray;
import net.coljate.list.impl.MutableNativeArray;
import net.coljate.list.impl.MutableWrappedArrayList;
import net.coljate.list.impl.RepeatedValueArray;
import net.coljate.list.impl.ReverseArrayIterator;

/**
 *
 * @author Ollie
 * @see java.util.List
 * @since 1.0
 */
public interface Array<T> extends Indexed<T>, List<T> {

    /**
     * @return the length of this array. It will be equal to or greater than the {@link #count}.
     */
    int length();

    @Override
    default T first() {
        return this.isEmpty() ? null : this.get(0);
    }

    @Override
    default T last() {
        return this.get(this.count());
    }

    @Override
    default ListIterator<T> iterator() {
        return new ArrayIterator<>(this);
    }

    @Override
    public default ListIterator<T> reverseIterator() {
        return new ReverseArrayIterator<>(this);
    }

    @Override
    default Array<T> reversedCopy() {
        return ImmutableNativeArray.reverseCopy(this);
    }

    @Override
    default java.util.ArrayList<T> mutableJavaCopy() {
        return this.mutableJavaCopy(java.util.ArrayList::new);
    }

    @Override
    default boolean contains(final Object object) {
        return List.super.contains(object);
    }

    @Override
    default void forEach(final Consumer<? super T> action) {
        for (int i = 0; i < this.count(); i++) {
            action.accept(this.get(i));
        }
    }

    @Override
    default MutableArray<T> mutableCopy() {
        return MutableWrappedArrayList.viewOf(this.mutableJavaCopy());
    }

    @Override
    default ImmutableArray<T> immutableCopy() {
        return ImmutableNativeArray.copyOf(this);
    }

    static <T> ImmutableArray<T> repeated(final T value, final int length) {
        return RepeatedValueArray.of(value, length);
    }

    static <T> Array<T> of() {
        return ImmutableArray.of();
    }

    static <T> Array<T> viewOf(final T[] elements) {
        return MutableNativeArray.viewOf(elements);
    }

    static <T> Array<T> viewOf(final T[] elements, final int fromInclusive, final int toExlusive) {
        throw new UnsupportedOperationException(); //TODO
    }

    @SafeVarargs
    static <T> Array<T> copyOf(final T... elements) {
        return ImmutableNativeArray.copyOf(elements);
    }

    static <T> Array<T> copyOf(final Collection<? extends T> collection) {
        return ImmutableArray.copyOf(collection);
    }

}
