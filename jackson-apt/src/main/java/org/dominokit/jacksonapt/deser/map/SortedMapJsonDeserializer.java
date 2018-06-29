/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dominokit.jacksonapt.deser.map;

import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.deser.map.key.KeyDeserializer;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Default {@link JsonDeserializer} implementation for {@link SortedMap}. The deserialization process returns a {@link TreeMap}.
 * <p>Cannot be overriden. Use {@link BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link SortedMap}
 * @param <V> Type of the values inside the {@link SortedMap}
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class SortedMapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<SortedMap<K, V>, K, V> {

    /**
     * <p>newInstance</p>
     *
     * @param keyDeserializer   {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     * @param <K>               Type of the keys inside the {@link SortedMap}
     * @param <V>               Type of the values inside the {@link SortedMap}
     * @return a new instance of {@link SortedMapJsonDeserializer}
     */
    public static <K, V> SortedMapJsonDeserializer<K, V> newInstance(KeyDeserializer<K> keyDeserializer,
                                                                     JsonDeserializer<V> valueDeserializer) {
        return new SortedMapJsonDeserializer<K, V>(keyDeserializer, valueDeserializer);
    }

    /**
     * @param keyDeserializer   {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private SortedMapJsonDeserializer(KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer) {
        super(keyDeserializer, valueDeserializer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SortedMap<K, V> newMap() {
        return new TreeMap<K, V>();
    }
}
