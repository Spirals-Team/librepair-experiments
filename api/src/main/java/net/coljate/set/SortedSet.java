package net.coljate.set;

import java.util.Comparator;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import net.coljate.collection.Collection;
import net.coljate.collection.SortedCollection;
import net.coljate.set.impl.MutableWrappedTreeSet;
import net.coljate.set.lazy.LazyFilteredSortedSet;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;

/**
 *
 * @author Ollie
 * @see java.util.SortedSet
 */
public interface SortedSet<T> extends SortedCollection<T>, SequentialSet<T> {

    @Override
    @Deprecated
    default T first() {
        return SortedCollection.super.first();
    }

    @Override
    @Deprecated
    default T last() {
        return this.greatest();
    }

    @Override
    default SortedSet<T> filter(final Predicate<? super T> predicate) {
        return LazyFilteredSortedSet.of(this, predicate);
    }

    @Nonnull
    @CheckReturnValue
    default SortedSet<T> greaterThan(final T element, final boolean inclusive) {
        final IntPredicate comparison = inclusive ? i -> i >= 0 : i -> i > 0;
        return this.filter(e -> comparison.test(this.comparator().compare(e, element)));
    }

    @Nonnull
    @CheckReturnValue
    default SortedSet<T> lessThan(final T element, final boolean inclusive) {
        final IntPredicate comparison = inclusive ? i -> i <= 0 : i -> i < 0;
        return this.filter(e -> comparison.test(this.comparator().compare(e, element)));
    }

    @TimeComplexity(computed = true, bestCase = Complexity.LINEAR)
    static <T> SortedSet<T> copyOf(
            final Comparator<? super T> comparator,
            final Collection<? extends T> collection) {
        return MutableWrappedTreeSet.copyOf(comparator, collection);
    }

}
