package net.coljate.map;

/**
 *
 * @author Ollie
 */
public interface MutableEntry<K, V> extends Entry<K, V> {

    void setValue(V value);

    default V getAndSetValue(final V value) {
        final V previous = this.value();
        this.setValue(value);
        return previous;
    }

}
