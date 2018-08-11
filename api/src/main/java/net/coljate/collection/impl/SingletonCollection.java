package net.coljate.collection.impl;

import java.util.NoSuchElementException;
import java.util.Objects;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;
import net.coljate.collection.MutableCollection;
import net.coljate.collection.Singleton;
import net.coljate.util.iterator.UnmodifiableIterator;

/**
 *
 * @author Ollie
 */
public class SingletonCollection<T>
        extends AbstractCollection<T>
        implements Singleton<T> {

    public static <T> SingletonCollection<T> of(final T element) {
        return new SingletonCollection<>(element);
    }

    private final T element;

    protected SingletonCollection(final T element) {
        this.element = element;
    }

    @Override
    public T element() {
        return element;
    }

    @Override
    public boolean contains(final Object object) {
        return Objects.equals(this.element(), object);
    }

    @Override
    public MutableCollection<T> mutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T first() {
        return element;
    }
    
    @Override
    public UnmodifiableIterator<T> iterator() {
        return new UnmodifiableIterator<T>() {

            boolean done = false;

            @Override
            public boolean hasNext() {
                return !done;
            }

            @Override
            public T next() {
                if (done) {
                    throw new NoSuchElementException();
                }
                done = true;
                return element;
            }

        };
    }

    @Override
    @Deprecated
    public SingletonCollection<T> immutableCopy() {
        return this;
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    @Override
    protected boolean equals(final Collection<?> that) {
        return that instanceof SingletonCollection
                && Objects.equals(this.element(), ((Singleton) that).element());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.element);
        return hash;
    }

}
