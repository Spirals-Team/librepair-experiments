package net.coljate.list.lazy;

import net.coljate.collection.Collection;
import net.coljate.collection.lazy.LazyFilteredCollection;
import net.coljate.list.List;
import net.coljate.list.ListIterator;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Ollie
 */
public class LazyFilteredList<T>
        extends LazyFilteredCollection<T>
        implements LazyList<T> {

    public static <T> Function<Collection<T>, ? extends LazyFilteredList<T>> filter(final Predicate<? super T> predicate) {
        return list -> new LazyFilteredList<>(List.copyOrCast(list), predicate);
    }

    private final List<? extends T> list;

    public LazyFilteredList(final List<? extends T> list, final Predicate<? super T> predicate) {
        super(list, predicate);
        this.list = list;
    }

    @Override
    public ListIterator<T> iterator() {
        return new FilteredListIterator<>(list.iterator(), this.predicate());
    }

    @Override
    public ListIterator<T> reverseIterator() {
        return new FilteredListIterator<>(list.reverseIterator(), this.predicate());
    }

    @Override
    public T last() {
        throw new UnsupportedOperationException(); //TODO
    }

    private static class FilteredListIterator<T> implements ListIterator<T> {

        private final ListIterator<? extends T> delegate;
        private final Predicate<? super T> predicate;

        private boolean hasNext;
        private T next;

        FilteredListIterator(final ListIterator<? extends T> delegate, final Predicate<? super T> predicate) {
            this.delegate = delegate;
            this.predicate = predicate;
        }

        @Override
        public boolean hasNext() {
            if (hasNext) {
                return true;
            }
            while (delegate.hasNext()) {
                T next = delegate.next();
                if (predicate.test(next)) {
                    this.hasNext = true;
                    this.next = next;
                    return true;
                }
            }
            return false;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            hasNext = false;
            return next;
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException(); //TODO
        }

        @Override
        public T previous() {
            throw new UnsupportedOperationException(); //TODO
        }

    }

}
