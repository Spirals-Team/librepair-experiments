package net.coljate.util;

import java.util.OptionalInt;
import java.util.function.IntFunction;

import net.coljate.IterableExtension;
import net.coljate.collection.Collection;

/**
 *
 * @author Ollie
 */
public class NativeCollections {

    public static <T> java.util.List<T> asUnmodifiableList(final java.util.Collection<T> collection) {
        return java.util.Collections.unmodifiableList(collection instanceof java.util.List
                ? ((java.util.List<T>) collection)
                : new java.util.ArrayList<>(collection));
    }

    public static <T> java.util.Collection<T> asCollection(final Iterable<T> iterable) {
        return iterable instanceof java.util.Collection
                ? (java.util.Collection<T>) iterable
                : asArrayList(iterable);
    }

    public static <T> java.util.ArrayList<T> asArrayList(final Iterable<T> iterable) {
        return iterable instanceof java.util.ArrayList
                ? (java.util.ArrayList<T>) iterable
                : copyIntoArrayList(iterable);
    }

    public static <T> java.util.ArrayList<T> copyIntoArrayList(final Iterable<? extends T> iterable) {
        return copy(iterable, java.util.ArrayList::new);
    }

    public static <T, C extends java.util.Collection<T>> C copy(
            final Iterable<? extends T> iterable,
            final IntFunction<C> createCollection) {
        final C collection = createCollection.apply(maybeCount(iterable).orElse(10));
        iterable.forEach(collection::add);
        return collection;
    }

    public static OptionalInt maybeCount(final Iterable<?> iterable) {
        if (iterable instanceof java.util.Collection) {
            return OptionalInt.of(((java.util.Collection) iterable).size());
        }
        if (iterable instanceof Collection) {
            return OptionalInt.of(((IterableExtension) iterable).count());
        }
        return OptionalInt.empty();
    }
    
}
