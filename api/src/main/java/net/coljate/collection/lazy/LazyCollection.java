package net.coljate.collection.lazy;

import net.coljate.collection.Collection;
import net.coljate.collection.ImmutableCollection;
import net.coljate.collection.MutableCollection;
import net.coljate.list.ImmutableArray;
import net.coljate.list.impl.MutableWrappedArrayList;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author Ollie
 */
public interface LazyCollection<T> extends Collection<T> {

    @Override
    default MutableCollection<T> mutableCopy() {
        return MutableWrappedArrayList.copyOf(this);
    }

    @Override
    default ImmutableCollection<T> immutableCopy() {
        return ImmutableArray.copyOf(this);
    }

    static <T> Function<Collection<T>, ? extends LazyCollection<T>> lazyFilter(final Predicate<? super T> predicate) {
        return collection -> new LazyFilteredCollection<>(collection, predicate);
    }

    static <F, T> Function<Collection<F>, ? extends LazyCollection<T>> lazyTransform(final Function<? super F, ? extends T> transform) {
        return collection -> new LazyTransformedCollection<>(collection, transform);
    }

}
