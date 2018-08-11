package net.coljate.list;

import java.util.Spliterator;
import java.util.Spliterators;

import net.coljate.collection.Collection;
import net.coljate.collection.ImmutableCollection;
import net.coljate.list.impl.EmptyArray;
import net.coljate.list.impl.ImmutableJoinList;
import net.coljate.list.impl.ImmutableWrappedList;
import net.coljate.list.impl.SingletonArray;

/**
 *
 * @author Ollie
 */
public interface ImmutableList<T> extends List<T>, ImmutableCollection<T> {

    @Override
    ImmutableListIterator<T> iterator();

    @Override
    default ImmutableListIterator<T> reverseIterator() {
        return this.reversedCopy().iterator();
    }

    @Override
    default ImmutableList<T> reversedCopy() {
        return List.super.reversedCopy().immutableCopy();
    }

    @Override
    @Deprecated
    default ImmutableList<T> immutableCopy() {
        return this;
    }

    default ImmutableList<T> prefixed(final T element) {
        return this.prefixed(ImmutableList.of(element));
    }

    default ImmutableList<T> prefixed(final List<? extends T> list) {
        return join(list.immutableCopy(), this);
    }

    default ImmutableList<T> suffixed(final T element) {
        return this.suffixed(ImmutableList.of(element));
    }

    default ImmutableList<T> suffixed(final List<? extends T> list) {
        return join(this, list.immutableCopy());
    }

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.count(), Spliterator.SIZED | Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }

    static <T> EmptyArray<T> of() {
        return ImmutableArray.of();
    }

    static <T> SingletonArray<T> of(final T element) {
        return ImmutableArray.of(element);
    }

    @SafeVarargs
    static <T> ImmutableList<T> of(final T... elements) {
        return ImmutableArray.of(elements);
    }

    static <T> ImmutableList<T> copyOf(final java.util.Collection<? extends T> collection) {
        return ImmutableWrappedList.copyOf(collection);
    }

    static <T> ImmutableList<T> copyOf(final Collection<? extends T> collection) {
        return ImmutableArray.copyOf(collection);
    }

    @SuppressWarnings("unchecked")
    static <T> ImmutableList<T> join(
            final ImmutableList<? extends T> left,
            final ImmutableList<? extends T> right) {
        return ImmutableJoinList.of(left, right);
    }

}
