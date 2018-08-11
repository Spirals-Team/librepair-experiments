package net.coljate.map.primitive;

import javax.annotation.CheckForNull;

import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.set.primitive.DoubleSet;
import net.coljate.util.iterator.CovariantIterator;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public interface DoubleKeyMap<V> extends Map<Double, V> {

    @CheckForNull
    DoubleKeyEntry<V> getEntry(double key);

    default boolean containsKey(final double key) {
        return this.getEntry(key) != null;
    }

    @Override
    @Deprecated
    default DoubleKeyEntry<V> getEntry(final Object key) {
        return key instanceof Double
                ? this.getEntry((double) key)
                : null;
    }

    @Override
    DoubleSet keys();

    @Override
    default CovariantIterator<Entry<Double, V>, ? extends DoubleKeyEntry<V>> iterator() {
        return Iterators.transform(this.keys().iterator(), this::getEntry);
    }

    interface DoubleKeyEntry<V> extends Entry<Double, V> {

        double doubleKey();

        @Override
        @Deprecated
        default Double key() {
            return this.doubleKey();
        }

    }

}
