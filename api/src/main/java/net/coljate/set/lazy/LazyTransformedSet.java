package net.coljate.set.lazy;

import java.util.Iterator;
import java.util.function.Function;

import net.coljate.collection.Collection;
import net.coljate.collection.lazy.LazyTransformedCollection;
import net.coljate.set.MutableSet;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class LazyTransformedSet<F, T>
        extends LazyTransformedCollection<F, T>
        implements LazySet<T> {

    public LazyTransformedSet(final Collection<F> source, final Function<? super F, ? extends T> transformation) {
        super(source, transformation);
    }

    @Override
    public Iterator<T> iterator() {
        final MutableSet<T> seen = MutableSet.createHashSet(this.count());
        return Iterators.filter(super.iterator(), seen::add);
    }

}
