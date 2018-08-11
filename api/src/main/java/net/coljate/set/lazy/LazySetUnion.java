package net.coljate.set.lazy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.annotation.Nullable;

import net.coljate.set.AbstractSet;
import net.coljate.set.Set;

/**
 * View of the union of two (possibly mutable) sets.
 *
 * @author Ollie
 */
public class LazySetUnion<T>
        extends AbstractSet<T>
        implements LazySet<T> {

    @SuppressWarnings("unchecked")
    public static <T> LazySet<T> of(final Set<? extends T> s1, final Set<? extends T> s2) {
        return new LazySetUnion<>(s1, s2);
    }

    private final Set<? extends T> s1, s2;

    protected LazySetUnion(final Set<? extends T> s1, Set<? extends T> s2) {
        this.s1 = Objects.requireNonNull(s1);
        this.s2 = Objects.requireNonNull(s2);
    }

    @Override
    public boolean contains(final Object object) {
        return s1.contains(object) || s2.contains(object);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            boolean hasNext;
            @Nullable
            T next;
            final Iterator<? extends T> i1 = s1.iterator();
            Iterator<? extends T> i2;

            @Override
            public boolean hasNext() {
                if (i1.hasNext()) {
                    next = i1.next();
                    hasNext = true;
                    return true;
                }
                hasNext = false;
                if (i2 == null) {
                    i2 = s2.iterator();
                }
                while (i2.hasNext()) {
                    final T n = i2.next();
                    if (!s1.contains(n)) {
                        hasNext = true;
                        next = n;
                    }
                }
                return hasNext;
            }

            @Override
            public T next() {
                if (!hasNext) {
                    throw new NoSuchElementException();
                }
                return next;
            }

        };
    }

}
