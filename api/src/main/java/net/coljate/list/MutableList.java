package net.coljate.list;

import java.util.Iterator;
import java.util.Objects;

import net.coljate.collection.Collection;
import net.coljate.collection.MutableCollection;
import net.coljate.list.impl.MutableNativeArray;
import net.coljate.list.impl.MutableWrappedArrayList;
import net.coljate.list.impl.MutableWrappedList;
import net.coljate.list.impl.SynchronizedList;

/**
 *
 * @author Ollie
 */
public interface MutableList<T> extends List<T>, MutableCollection<T> {

    void prefix(T element);

    void suffix(T element);

    default void suffixAll(final Iterable<? extends T> iterable) {
        iterable.forEach(this::suffix);
    }

    @Override
    @Deprecated
    default boolean add(final T element) {
        this.suffix(element);
        return true;
    }

    @Override
    @Deprecated
    default boolean addAll(final Iterable<? extends T> iterable) {
        this.suffixAll(iterable);
        return true;
    }

    default boolean removeLast(final Object element) {
        for (final Iterator<T> iterator = this.reverseIterator(); iterator.hasNext();) {
            if (Objects.equals(iterator.next(), element)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    default SynchronizedList<T> synchronizedCopy() {
        return synchronize(this);
    }

    static <T> MutableList<T> create(final int size) {
        return MutableWrappedArrayList.create(size);
    }

    @SafeVarargs
    static <T> MutableList<T> of(final T... elements) {
        return MutableNativeArray.viewOf(elements);
    }

    static <T> MutableList<T> copyIntoArray(final Collection<? extends T> collection) {
        return MutableArray.copyOf(collection);
    }

    static <T> MutableList<T> viewOf(final java.util.List<T> list) {
        return new MutableWrappedList<>(list);
    }

    static <T> SynchronizedList<T> synchronize(final MutableList<T> list) {
        return new SynchronizedList<>(list);
    }

}
