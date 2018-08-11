package net.coljate.counter.lazy;

import java.util.Iterator;
import java.util.Objects;

import net.coljate.collection.Collection;
import net.coljate.counter.AbstractCounter;
import net.coljate.map.Map;
import net.coljate.map.MutableMap;
import net.coljate.set.Set;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class LazyElementCounter<T>
        extends AbstractCounter<T>
        implements LazyCounter<T> {

    private final Collection<? extends T> collection;

    public LazyElementCounter(final Collection<? extends T> collection) {
        this.collection = collection;
    }

    @Override
    public boolean contains(final Object element) {
        return collection.contains(element);
    }

    @Override
    @TimeComplexity(Complexity.LINEAR)
    public int count(final Object element) {
        return collection.count(object -> Objects.equals(object, element));
    }

    @Override
    public Set<T> elements() {
        return Set.viewOf(collection.distinct());
    }

    @Override
    @TimeComplexity(Complexity.LINEAR)
    public Map<T, Integer> countElements() {
        final MutableMap<T, Integer> counter = MutableMap.createHashMap();
        for (final T element : collection) {
            counter.compute(element, ($, count) -> count == null ? 1 : count + 1);
        }
        return counter;
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.covariant(collection.iterator());
    }

}
