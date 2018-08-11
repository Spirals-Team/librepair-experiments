package net.coljate.set.impl;

import net.coljate.collection.Collection;
import net.coljate.set.ConcurrentSet;
import net.coljate.util.Native;
import static net.coljate.util.Native.createConcurrentHashSet;

/**
 *
 * @author Ollie
 */
public class ConcurrentWrappedSet<T>
        extends MutableWrappedSet<T>
        implements ConcurrentSet<T> {

    public static final int DEFAULT_INITIAL_CAPACITY = 10;

    public static <T> ConcurrentWrappedSet<T> createHashSet() {
        return createHashSet(DEFAULT_INITIAL_CAPACITY);
    }

    public static <T> ConcurrentWrappedSet<T> createHashSet(final int initialCapacity) {
        return new ConcurrentWrappedSet<>(Native.createConcurrentHashSet(initialCapacity));
    }

    public static <T> ConcurrentWrappedSet<T> copyIntoHashSet(final java.util.Collection<? extends T> collection) {
        final java.util.Set<T> set = createConcurrentHashSet(collection.size());
        collection.forEach(set::add);
        return new ConcurrentWrappedSet<>(set);
    }

    public static <T> ConcurrentWrappedSet<T> copyIntoHashSet(final Collection<? extends T> collection) {
        return new ConcurrentWrappedSet<>(collection.mutableJavaCopy(Native::createConcurrentHashSet));
    }

    protected ConcurrentWrappedSet(final java.util.Set<T> delegate) {
        super(delegate);
    }

    @Override
    public java.util.Set<T> mutableJavaCopy() {
        return this.mutableJavaCopy(Native::createConcurrentHashSet);
    }

    @Override
    public ConcurrentWrappedSet<T> mutableCopy() {
        return new ConcurrentWrappedSet<>(this.mutableJavaCopy());
    }

}
