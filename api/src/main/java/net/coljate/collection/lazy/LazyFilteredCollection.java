package net.coljate.collection.lazy;

import net.coljate.collection.Collection;
import net.coljate.util.iterator.Iterators;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * @author Ollie
 */
public class LazyFilteredCollection<T> implements LazyCollection<T> {

    private final Collection<? extends T> collection;
    private final Predicate<? super T> predicate;

    protected LazyFilteredCollection(final Collection<? extends T> collection, final Predicate<? super T> predicate) {
        this.collection = collection;
        this.predicate = predicate;
    }

    protected Predicate<? super T> predicate() {
        return predicate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(final Object object) {
        return collection.contains(object)
                && predicate.test((T) object); //"Ensures" correct type.
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.filter(collection.iterator(), predicate);
    }

}
