package net.coljate.list;

/**
 *
 * @author Ollie
 */
public interface SortedArray<T> extends SortedList<T>, Array<T> {

    @Override
    default T first() {
        return Array.super.first();
    }

    @Override
    default T last() {
        return Array.super.last();
    }

}
