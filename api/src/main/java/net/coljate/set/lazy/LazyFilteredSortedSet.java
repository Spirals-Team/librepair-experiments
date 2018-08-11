package net.coljate.set.lazy;

import java.util.Comparator;
import java.util.function.Predicate;

import net.coljate.set.SortedSet;

/**
 *
 * @author ollie
 */
public class LazyFilteredSortedSet<T>
        extends LazyFilteredSet<T>
        implements LazySortedSet<T> {

    @SuppressWarnings("unchecked")
    public static <T> SortedSet<T> of(final SortedSet<? extends T> set, final Predicate<? super T> predicate) {
        return set.isAlwaysEmpty()
                ? (SortedSet<T>) set
                : new LazyFilteredSortedSet<>(set, predicate);
    }

    private final SortedSet<? extends T> set;

    protected LazyFilteredSortedSet(final SortedSet<? extends T> set, final Predicate<? super T> predicate) {
        super(set, predicate);
        this.set = set;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Comparator<? super T> comparator() {
        return (Comparator<? super T>) set.comparator();
    }

    @Override
    public T greatest() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
