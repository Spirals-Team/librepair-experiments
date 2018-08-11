package net.coljate.set;

import net.coljate.collection.Collection;
import net.coljate.list.Array;
import net.coljate.set.impl.CovariantSet;
import net.coljate.set.impl.EmptySet;
import net.coljate.set.impl.MutableWrappedSet;
import net.coljate.set.impl.TwoSet;
import net.coljate.set.impl.UnmodifiableSet;
import net.coljate.set.impl.WrappedSet;
import net.coljate.set.lazy.LazyFilteredSet;
import net.coljate.set.lazy.LazySet;
import net.coljate.set.lazy.LazySetUnion;
import net.coljate.util.Equality;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;

import javax.annotation.CheckReturnValue;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Collector;

/**
 * A finite set of elements.
 *
 * @author Ollie
 * @since 1.0
 */
public interface Set<T> extends Collection<T> {

    @Override
    default MutableSet<T> mutableCopy() {
        return MutableWrappedSet.copyOf(this);
    }

    @Override
    default ImmutableSet<T> immutableCopy() {
        return ImmutableSet.copyOf(this);
    }

    @Override
    default java.util.Set<T> mutableJavaCopy() {
        return this.mutableJavaCopy(java.util.HashSet::new);
    }

    @Override
    @TimeComplexity(computed = true, bestCase = Complexity.LINEAR)
    @CheckReturnValue
    default SortedSet<T> sortedCopy(final Comparator<? super T> comparator) {
        return SortedSet.copyOf(comparator, this);
    }

    default boolean elementsEqual(final Set<?> that) {
        return that != null && Set.elementsEqual(this, that);
    }

    @Override
    @CheckReturnValue
    default Set<T> filter(final Predicate<? super T> predicate) {
        return LazyFilteredSet.of(this, predicate);
    }

    /**
     * "And" operator.
     *
     * @param element
     * @return a set containing the given element if it is contained in this set, otherwise an empty set.
     */
    @CheckReturnValue
    default Set<T> intersection(final T element) {
        return this.contains(element)
                ? Set.of(element)
                : Set.of();
    }

    /**
     * Intersection.
     *
     * @return a set of elements in both this set and the given set.
     */
    @CheckReturnValue
    default Set<T> intersection(final Set<? extends T> that) {
        return that.isAlwaysEmpty()
                ? this
                : LazySet.intersection(this, that);
    }

    /**
     * Relative complement.
     *
     * @return a set of elements in this set but not the given set.
     */
    @CheckReturnValue
    default Set<T> not(final Set<? extends T> that) {
        return LazySet.complement(this, that);
    }

    default Set<T> not(final T element) {
        return this.contains(element)
                ? this.not(of(element))
                : this;
    }

    /**
     * "Or" operator.
     *
     * @param element
     * @return
     */
    default Set<T> union(final T element) {
        return this.contains(element)
                ? this
                : this.union(Set.of(element));
    }

    /**
     * Union.
     *
     * @return a set of elements in either this set or the given set, or both.
     */
    @CheckReturnValue
    default Set<T> union(final Set<? extends T> that) {
        return LazySetUnion.of(this, that);
    }

    /**
     * Symmetric difference.
     *
     * @return a set of elements in either this set or the given set, but not both.
     */
    @CheckReturnValue
    default Set<T> xor(final Set<? extends T> that) {
        return LazySet.difference(this, that);
    }

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.count(), Spliterator.SIZED | Spliterator.DISTINCT);
    }

    @Override
    @Deprecated
    default Set<T> distinct() {
        return this;
    }

    static <T> EmptySet<T> of() {
        return ImmutableSet.of();
    }

    static <T> Set<T> of(final T element) {
        return ImmutableSet.of(element);
    }

    static <T> Set<T> of(final T t1, final T t2) {
        return TwoSet.of(t1, t2);
    }

    @SafeVarargs
    static <T> Set<T> of(final T... elements) {
        return ImmutableSet.of(elements);
    }

    static <T> Set<T> ofNonNull(final T a, final T b) {
        return ImmutableSet.ofNonNull(a, b);
    }

    static <T> Set<T> unmodifiable(final Set<? extends T> set) {
        return UnmodifiableSet.viewOf(set);
    }

    static <T> Set<T> copyOrCast(final Collection<T> collection) {
        return collection instanceof Set
                ? (Set<T>) collection
                : copyOf(collection);
    }

    static <T> Set<T> copyIntoHashSet(final Iterable<? extends T> iterable) {
        return MutableWrappedSet.copyIntoHashSet(iterable);
    }

    static <T> Set<T> copyIntoHashSet(final java.util.Collection<? extends T> collection) {
        return MutableWrappedSet.copyIntoHashSet(collection);
    }

    static <T> Set<T> copyIntoHashSet(final Collection<? extends T> collection) {
        return MutableWrappedSet.copyIntoHashSet(collection);
    }

    static <T> Set<T> flattenIntoHashSet(final Iterable<? extends Iterable<? extends T>> nested) {
        final MutableSet<T> set = MutableSet.createHashSet();
        nested.forEach(set::addAll);
        return set;
    }

    static <T> MutableSet<T> createHashSet() {
        return MutableSet.createHashSet();
    }

    /**
     * @param <T>
     * @param set
     * @return a covariant view of the given set.
     */
    static <T> Set<T> viewOf(final Set<? extends T> set) {
        return new CovariantSet<>(set);
    }

    static <T> Set<T> copyOf(final Collection<? extends T> collection) {
        return ImmutableSet.copyOf(collection);
    }

    static <T> Set<T> viewOf(final java.util.Set<? extends T> set) {
        return new WrappedSet<>(set);
    }

    static <T> Set<T> ofOptional(final Optional<? extends T> optional) {
        return optional.map(Set::<T>of).orElseGet(Set::of);
    }

    @SafeVarargs
    static <T> Set<T> ofOptionals(final Optional<? extends T>... optionals) {
        return Array.viewOf(optionals)
                .filter(Optional::isPresent)
                .<T>transform(Optional::get)
                .distinct();
    }

    @SafeVarargs
    static <T> Set<T> ofNonNull(final T... elements) {
        return of(elements).filter(Objects::nonNull);
    }

    static int elementHash(final Set<?> set) {
        throw new UnsupportedOperationException();
    }

    static boolean elementsEqual(final Set<?> s1, final Set<?> s2) {
        return Equality.unorderedEquals(s1, s2);
    }

    static <T> Collector<T, ?, ? extends Set<T>> collector() {
        return ImmutableSet.collector();
    }

}
