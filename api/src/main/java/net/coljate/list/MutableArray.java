package net.coljate.list;

import java.util.Objects;

import net.coljate.collection.Collection;
import net.coljate.list.impl.MutableNativeArray;

/**
 *
 * @author Ollie
 */
public interface MutableArray<T> extends Array<T>, MutableList<T> {

    T set(int i, T value);

    void resize(int length);

    default boolean replace(final int index, final T expectedValue, final T replacementValue) {
        if (Objects.equals(this.get(index), expectedValue)) {
            this.set(index, replacementValue);
            return true;
        } else {
            return false;
        }
    }

    static <T> MutableArray<T> create(final int length) {
        return MutableNativeArray.create(length);
    }

    static <T> MutableArray<T> copyOf(final T[] elements) {
        return MutableNativeArray.copyOf(elements);
    }

    static <T> MutableArray<T> copyOf(final Collection<? extends T> collection) {
        return MutableNativeArray.copyOf(collection);
    }

    @SafeVarargs
    static <T> MutableArray<T> viewOf(final T... elements) {
        return MutableNativeArray.viewOf(elements);
    }

}
