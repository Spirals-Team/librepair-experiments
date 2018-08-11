package net.coljate.set.impl;

import net.coljate.map.ImmutableMap;
import net.coljate.map.Map;
import net.coljate.set.ImmutableSet;
import net.coljate.set.Set;
import net.coljate.util.iterator.UnmodifiableIterator;

/**
 *
 * @author Ollie
 */
public class ImmutableMapBackedSet<T>
        extends MapBackedSet<T>
        implements ImmutableSet<T> {

    public static <T> ImmutableMapBackedSet<T> copyOf(final Set<T> set) {
        return copyOf(Map.repeat(set, SENTINEL));
    }

    public static <T> ImmutableMapBackedSet<T> copyOf(final Map<T, Object> map) {
        return new ImmutableMapBackedSet<>(map.immutableCopy());
    }

    private final ImmutableMap<T, Object> map;

    protected ImmutableMapBackedSet(final ImmutableMap<T, Object> map) {
        super(map);
        this.map = map;
    }

    @Override
    public ImmutableMapBackedSet<T> with(final T element) {
        return this.contains(element)
                ? this
                : new ImmutableMapBackedSet<>(map.with(element, SENTINEL));
    }

    @Override
    public UnmodifiableIterator<T> iterator() {
        return UnmodifiableIterator.wrap(super.iterator());
    }

}
