package net.coljate.set.lazy;

import java.util.function.Predicate;

import net.coljate.collection.lazy.LazyFilteredCollection;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class LazyFilteredSet<T>
        extends LazyFilteredCollection<T>
        implements LazySet<T> {

    public static <T> LazySet<T> of(final Set<? extends T> set, final Predicate<? super T> predicate) {
        return new LazyFilteredSet<>(set, predicate);
    }

    protected LazyFilteredSet(final Set<? extends T> set, final Predicate<? super T> predicate) {
        super(set, predicate);
    }

}
