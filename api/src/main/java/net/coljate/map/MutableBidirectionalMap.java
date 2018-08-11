package net.coljate.map;

/**
 *
 * @author Ollie
 */
public interface MutableBidirectionalMap<K, V>
        extends BidirectionalMap<K, V>, MutableMap<K, V> {

    @Override
    MutableBidirectionalMap<V, K> inverseView();

    /**
     *
     * @param key
     * @param value
     * @return
     * @throws MutableBidirectionalMap.DuplicateValueException
     */
    @Override
    V put(K key, V value) throws DuplicateValueException;

    /**
     *
     * @param key
     * @param value
     * @return the key/value entry previously associated.
     * @throws MutableBidirectionalMap.DuplicateValueException
     */
    default Entry<K, V> putBoth(final K key, final V value) throws DuplicateValueException {
        final K oldKey = this.inverseView().put(value, key);
        final V oldValue = this.put(key, value);
        return Entry.of(oldKey, oldValue);
    }

    class DuplicateValueException extends IllegalArgumentException {

        private static final long serialVersionUID = 1L;

        public DuplicateValueException(final Object duplicate) {
            super("Duplicate value: " + duplicate);
        }

    }

}
