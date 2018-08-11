package net.coljate.set.impl;

import net.coljate.set.AbstractSet;
import net.coljate.set.ImmutableSet;
import net.coljate.set.SequentialSet;
import net.coljate.util.iterator.UnmodifiableIterator;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author Ollie
 */
public class TwoSet<T>
        extends AbstractSet<T>
        implements ImmutableSet<T>, SequentialSet<T> {

    public static <T> ImmutableSet<T> of(final T a, final T b) {
        return Objects.equals(a, b)
                ? SingletonSet.of(a)
                : new TwoSet<>(a, b);
    }

    public static <T> TwoSet<T> require(final T a, final T b) {
        return new TwoSet<>(a, b);
    }

    private final T a, b;

    protected TwoSet(final T a, final T b) {
        if (Objects.equals(a, b)) {
            throw new IllegalArgumentException("Cannot construct two-set with [" + a + "] = [" + b + "]!");
        }
        this.a = a;
        this.b = b;
    }

    @Override
    public T first() {
        return a;
    }

    @Override
    public T last() {
        return b;
    }

    @Override
    public boolean contains(final Object object) {
        return Objects.equals(a, object) || Objects.equals(b, object);
    }

    public boolean contains(final Object o1, final Object o2) {
        return Objects.equals(a, o1)
                ? Objects.equals(b, o2)
                : Objects.equals(b, o1) && Objects.equals(a, o2);
    }

    @Override
    public UnmodifiableIterator<T> iterator() {
        return new TwoSetIterator();
    }

    private final class TwoSetIterator implements UnmodifiableIterator<T> {

        private boolean doneFirst, doneSecond;

        @Override
        public boolean hasNext() {
            return !doneSecond;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (!doneFirst) {
                doneFirst = true;
                return a;
            } else {
                doneSecond = true;
                return b;
            }
        }

    }

}
