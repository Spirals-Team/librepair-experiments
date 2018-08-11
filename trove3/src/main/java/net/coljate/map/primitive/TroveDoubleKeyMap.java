package net.coljate.map.primitive;

import net.coljate.collection.Collection;
import net.coljate.map.AbstractMap;
import net.coljate.set.primitive.DoubleSet;
import net.coljate.set.primitive.TroveDoubleSet;

import gnu.trove.map.TDoubleObjectMap;

/**
 *
 * @author Ollie
 */
public class TroveDoubleKeyMap<V> extends AbstractMap<Double, V> implements DoubleKeyMap<V> {

    private final TDoubleObjectMap<V> map;
    private DoubleSet keys;
    private Collection<V> values;

    protected TroveDoubleKeyMap(final TDoubleObjectMap<V> map) {
        this.map = map;
    }

    @Override
    public boolean containsKey(final double key) {
        return map.containsKey(key);
    }

    @Override
    public DoubleKeyEntry<V> getEntry(final double key) {
        return map.containsKey(key)
                ? new Entry(key)
                : null;
    }

    @Override
    public DoubleSet keys() {
        return keys == null
                ? (keys = TroveDoubleSet.viewOf(map.keySet()))
                : keys;
    }

    @Override
    public Collection<V> values() {
        return values == null
                ? (values = Collection.viewOf(map.valueCollection()))
                : values;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    private final class Entry implements DoubleKeyEntry<V> {

        private final double key;

        Entry(final double key) {
            this.key = key;
        }

        @Override
        public double doubleKey() {
            return key;
        }

        @Override
        public V value() {
            return map.get(key);
        }

    }

}
