/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.glassfish.jersey.internal.guava;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Basic implementation of the {@link SetMultimap} interface. It's a wrapper
 * around {@link AbstractMapBasedMultimap} that converts the returned collections into
 * {@code Sets}. The {@link #createCollection} method must return a {@code Set}.
 *
 * @author Jared Levy
 */
abstract class AbstractSetMultimap<K, V>
        extends AbstractMapBasedMultimap<K, V> implements SetMultimap<K, V> {
    private static final long serialVersionUID = 7431625294878419160L;

    /**
     * Creates a new multimap that uses the provided map.
     *
     * @param map place to store the mapping from each key to its corresponding
     *            values
     */
    AbstractSetMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    abstract Set<V> createCollection();

    // Following Javadoc copied from SetMultimap.

    @Override
    Set<V> createUnmodifiableEmptyCollection() {
        return Collections.emptySet();
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>Because a {@code SetMultimap} has unique values for a given key, this
     * method returns a {@link Set}, instead of the {@link Collection} specified
     * in the {@link Multimap} interface.
     */
    @Override
    public Set<V> get(K key) {
        return (Set<V>) super.get(key);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>Because a {@code SetMultimap} has unique values for a given key, this
     * method returns a {@link Set}, instead of the {@link Collection} specified
     * in the {@link Multimap} interface.
     */
    @Override
    public Set<Map.Entry<K, V>> entries() {
        return (Set<Map.Entry<K, V>>) super.entries();
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>Because a {@code SetMultimap} has unique values for a given key, this
     * method returns a {@link Set}, instead of the {@link Collection} specified
     * in the {@link Multimap} interface.
     */
    @Override
    public Set<V> removeAll(Object key) {
        return (Set<V>) super.removeAll(key);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <p>Though the method signature doesn't say so explicitly, the returned map
     * has {@link Set} values.
     */
    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    /**
     * Stores a key-value pair in the multimap.
     *
     * @param key   key to store in the multimap
     * @param value value to store in the multimap
     * @return {@code true} if the method increased the size of the multimap, or
     * {@code false} if the multimap already contained the key-value pair
     */
    @Override
    public boolean put(K key, V value) {
        return super.put(key, value);
    }

    /**
     * Compares the specified object to this multimap for equality.
     * <p>
     * <p>Two {@code SetMultimap} instances are equal if, for each key, they
     * contain the same values. Equality does not depend on the ordering of keys
     * or values.
     */
    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }
}
