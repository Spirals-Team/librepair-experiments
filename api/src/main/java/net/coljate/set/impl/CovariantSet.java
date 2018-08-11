package net.coljate.set.impl;

import java.util.Iterator;

import net.coljate.set.AbstractSet;
import net.coljate.set.Set;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author ollie
 */
public class CovariantSet<T> extends AbstractSet<T> {

    private final Set<? extends T> set;

    public CovariantSet(Set<? extends T> set) {
        this.set = set;
    }

    @Override
    public boolean contains(final Object object) {
        return set.contains(object);
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.covariant(set.iterator());
    }

}
