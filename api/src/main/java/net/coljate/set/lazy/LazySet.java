package net.coljate.set.lazy;

import java.util.function.Function;
import java.util.function.Predicate;

import net.coljate.collection.Collection;
import net.coljate.collection.lazy.LazyCollection;
import net.coljate.set.ImmutableSet;
import net.coljate.set.MutableSet;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public interface LazySet<T> extends LazyCollection<T>, Set<T> {

    @Override
    default MutableSet<T> mutableCopy() {
        return Set.super.mutableCopy();
    }

    @Override
    default ImmutableSet<T> immutableCopy() {
        return Set.super.immutableCopy();
    }

    static <T> LazySet<T> filter(
            final Collection<T> collection,
            final Predicate<? super T> predicate) {
        return LazyFilteredSet.of(Set.copyOrCast(collection), predicate);
    }

    @SuppressWarnings("conditional.type.incompatible")
    static <T> Function<Collection<T>, LazySet<T>> transform() {
        return collection -> collection instanceof LazySet
                ? (LazySet<T>) collection
                : transform(collection, Function.identity());
    }

    static <F, T> Function<Collection<F>, LazySet<T>> transform(final Function<? super F, ? extends T> transformation) {
        return collection -> transform(collection, transformation);
    }

    static <F, T> LazySet<T> transform(
            final Collection<F> collection,
            final Function<? super F, ? extends T> transformation) {
        return new LazyTransformedSet<>(collection, transformation);
    }

    /**
     * Union {@code a ∪ b} of two sets. Elements must be in either set.
     */
    static <T> LazySet<T> union(final Set<? extends T> a, final Set<? extends T> b) {
        return LazySetUnion.of(a, b);
    }

    /**
     * Intersection {@code a ∩ b} of two sets. Elements must be in both sets.
     */
    static <T> LazySet<T> intersection(final Set<? extends T> a, final Collection<? extends T> b) {
        return LazyFilteredSet.of(a, element -> a.contains(element) && b.contains(element));
    }

    /**
     * Relative complement {@code a \ b} of two sets. Elements must be in the first set but not the second set.
     *
     * Note that the absolute complement can be an "infinite" set (if the universe is) so isn't supported.
     */
    static <T> LazySet<T> complement(final Set<? extends T> a, final Collection<? extends T> b) {
        return LazyFilteredSet.of(a, element -> a.contains(element) && !b.contains(element));
    }

    /**
     * Symmetric difference {@code a ∆ b} of two sets. Elements must be in either set but not both.
     */
    static <T> LazySet<T> difference(final Set<? extends T> a, final Set<? extends T> b) {
        return LazyFilteredSet.of(union(a, b), element -> a.contains(element) ^ b.contains(element));
    }

}
