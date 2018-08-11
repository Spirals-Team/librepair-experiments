package net.coljate.collection.lazy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import net.coljate.collection.Collection;

/**
 *
 * @author Ollie
 */
public class LazyAppendCollection<T> implements LazyCollection<T> {

    public static <T> Collection<T> of(final Collection<? extends T> collection, final T element) {
        return collection.isAlwaysEmpty()
                ? Collection.of(element)
                : new LazyAppendCollection<>(collection, element);
    }

    private final Collection<? extends T> collection;
    private final T element;

    protected LazyAppendCollection(final Collection<? extends T> collection, final T element) {
        this.collection = collection;
        this.element = element;
    }

    @Override
    public boolean contains(final Object object) {
        return Objects.equals(this.element, object)
                || collection.contains(object);
    }

    @Override
    public int count() {
        return collection.count() + 1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            final Iterator<? extends T> iterator = collection.iterator();
            boolean doneElement = false;

            @Override
            public boolean hasNext() {
                return iterator.hasNext() || !doneElement;
            }

            @Override
            public T next() {
                if (doneElement) {
                    throw new NoSuchElementException();
                }
                if (iterator.hasNext()) {
                    return iterator.next();
                }
                doneElement = true;
                return element;
            }

        };
    }

}
