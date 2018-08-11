package net.coljate.list;

import net.coljate.collection.Collection;
import net.coljate.list.impl.ArrayIterator;
import net.coljate.list.impl.EmptyArray;
import net.coljate.list.impl.ImmutableJoinArray;
import net.coljate.list.impl.ImmutableNativeArray;
import net.coljate.list.impl.SingletonArray;

/**
 * @author Ollie
 */
public interface ImmutableArray<T>
        extends Array<T>, ImmutableList<T> {

    default ImmutableArray<T> withPrefix(final Array<? extends T> array) {
        return ImmutableJoinArray.of(array.immutableCopy(), this);
    }

    default ImmutableArray<T> withSuffix(final Array<? extends T> array) {
        return ImmutableJoinArray.of(this, array.immutableCopy());
    }

    @Override
    default ImmutableListIterator<T> iterator() {
        return new ImmutableArrayIterator<>(this);
    }

    @Override
    default ImmutableListIterator<T> reverseIterator() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    default ImmutableArray<T> reversedCopy() {
        return ImmutableNativeArray.reverseCopy(this);
    }

    @Override
    @Deprecated
    default ImmutableArray<T> immutableCopy() {
        return this;
    }

    static <T> EmptyArray<T> empty() {
        return EmptyArray.instance();
    }

    static <T> EmptyArray<T> of() {
        return empty();
    }

    static <T> SingletonArray<T> of(final T element) {
        return SingletonArray.of(element);
    }

    @SafeVarargs
    static <T> ImmutableArray<T> of(final T... elements) {
        switch (elements.length) {
            case 0:
                return of();
            case 1:
                return of(elements[0]);
            default:
                return ImmutableNativeArray.copyOf(elements);
        }
    }

    static <T> ImmutableArray<T> copyOf(final T[] elements) {
        return of(elements);
    }

    static <T> ImmutableArray<T> copyOf(final Collection<? extends T> collection) {
        return ImmutableNativeArray.copyOf(collection);
    }

    class ImmutableArrayIterator<T> extends ArrayIterator<T> implements ImmutableListIterator<T> {

        public ImmutableArrayIterator(final Array<? extends T> array) {
            super(array);
        }

    }

}
