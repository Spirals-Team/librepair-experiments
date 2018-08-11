package net.coljate.map.primitive;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.DoubleSupplier;

import javax.annotation.Nonnull;

import net.coljate.collection.primitive.DoubleCollection;
import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.util.iterator.CovariantIterator;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public interface DoubleValueMap<K> extends Map<K, Double> {

    @Override
    DoubleValueEntry<K> getEntry(Object key);

    default double defaultValue() {
        return 0d;
    }

    default double getDouble(final K key) {
        return this.getDouble(key, this::defaultValue);
    }

    default double getDouble(final K key, final DoubleSupplier ifAbsent) {
        return this.maybeGetDouble(key).orElseGet(ifAbsent);
    }

    @Nonnull
    default OptionalDouble maybeGetDouble(final Object key) {
        final DoubleValueEntry<K> entry = this.getEntry(key);
        return entry == null ? OptionalDouble.empty() : OptionalDouble.of(entry.doubleValue());
    }

    @Override
    @Deprecated
    default Optional<Double> maybeGet(final K key) {
        final OptionalDouble optional = this.maybeGetDouble(key);
        return optional.isPresent()
                ? Optional.of(optional.getAsDouble())
                : Optional.empty();
    }

    @Override
    DoubleCollection values();

    @Override
    default CovariantIterator<Entry<K, Double>, ? extends DoubleValueEntry<K>> iterator() {
        return Iterators.transform(this.keys().iterator(), this::getEntry);
    }

    interface DoubleValueEntry<K> extends Entry<K, Double> {

        double doubleValue();

        @Override
        @Deprecated
        default Double value() {
            return this.doubleValue();
        }

    }

}
