package net.coljate.list;

import net.coljate.list.impl.MutableAtomicArray;

/**
 *
 * @author Ollie
 */
public interface ConcurrentArray<T>
        extends MutableArray<T>, ConcurrentList<T> {

    @Override
    ConcurrentArray<T> mutableCopy();

    @Override
    boolean replace(int index, T expectedValue, T replacementValue);

    static <T> ConcurrentArray<T> createConcurrentArray(final int length) {
        return MutableAtomicArray.create(length);
    }

}
