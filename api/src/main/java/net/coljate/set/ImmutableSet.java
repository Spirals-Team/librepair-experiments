package net.coljate.set;

import java.util.Collections;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collector;

import javax.annotation.Nonnull;

import net.coljate.collection.Collection;
import net.coljate.collection.ImmutableCollection;
import net.coljate.set.impl.EmptySet;
import net.coljate.set.impl.ImmutableWrappedSet;
import net.coljate.set.impl.SingletonSet;
import net.coljate.set.impl.TwoSet;
import net.coljate.set.lazy.ImmutableLazyUnionSet;

/**
 *
 * @author Ollie
 */
public interface ImmutableSet<T> extends Set<T>, ImmutableCollection<T> {

    @Nonnull
    default ImmutableSet<T> with(final T element) {
        return this.contains(element)
                ? this
                : ImmutableLazyUnionSet.of(this, of(element));
    }

    @Override
    @Deprecated
    default ImmutableSet<T> immutableCopy() {
        return this;
    }

    @Override
    default java.util.Set<T> mutableJavaCopy() {
        return Collections.unmodifiableSet(Set.super.mutableJavaCopy());
    }

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.count(), Spliterator.SIZED | Spliterator.DISTINCT | Spliterator.IMMUTABLE);
    }

    static <T> EmptySet<T> of() {
        return EmptySet.instance();
    }

    static <T> SingletonSet<T> of(final T element) {
        return SingletonSet.of(element);
    }

    static <T> ImmutableSet<T> ofNonNull(final T element) {
        return element == null ? of() : of(element);
    }

    static <T> ImmutableSet<T> of(final T a, final T b) {
        return TwoSet.of(a, b);
    }

    static <T> ImmutableSet<T> ofNonNull(final T a, final T b) {
        if (a == null && b == null) {
            return of();
        } else if (a == null) {
            return of(b);
        } else if (b == null) {
            return of(a);
        } else {
            return of(a, b);
        }
    }

    @SafeVarargs
    static <T> ImmutableSet<T> of(final T... elements) {
        switch (elements.length) {
            case 0:
                return of();
            case 1:
                return of(elements[0]);
            case 2:
                return of(elements[0], elements[1]);
            default:
                return ImmutableWrappedSet.copyIntoHashSet(elements);
        }
    }

    static <T> ImmutableSet<T> copyOf(final java.util.Collection<? extends T> collection) {
        return collection.isEmpty()
                ? of()
                : ImmutableWrappedSet.copyIntoHashSet(collection);
    }

    static <T> ImmutableSet<T> copyOf(final Collection<? extends T> collection) {
        return collection.isEmpty()
                ? of()
                : ImmutableWrappedSet.copyIntoHashSet(collection);
    }

    static <T> Collector<T, ?, ? extends ImmutableSet<T>> collector() {
        return Collector.<T, MutableSet<T>, ImmutableSet<T>>of(
                MutableSet::createHashSet,
                MutableSet::add,
                (l, r) -> {
                    l.addAll(r);
                    return l;
                },
                MutableSet::immutableCopy);
    }

}
