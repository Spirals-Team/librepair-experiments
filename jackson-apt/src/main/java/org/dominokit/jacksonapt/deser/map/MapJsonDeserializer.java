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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for {@link java.util.Map}. The deserialization process returns a {@link java.util.LinkedHashMap}.
 * <p>Cannot be overriden. Use {@link org.dominokit.jacksonapt.deser.map.BaseMapJsonDeserializer}.</p>
 *
 * @param <K> Type of the keys inside the {@link java.util.Map}
 * @param <V> Type of the values inside the {@link java.util.Map}
 * @author Nicolas Morel
 * @version $Id: $
 */
public final class MapJsonDeserializer<K, V> extends BaseMapJsonDeserializer<Map<K, V>, K, V> {

    /**
     * <p>newInstance</p>
     *
     * @param keyDeserializer   {@link org.dominokit.jacksonapt.deser.map.key.KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link org.dominokit.jacksonapt.JsonDeserializer} used to deserialize the values.
     * @param <K>               Type of the keys inside the {@link java.util.Map}
     * @param <V>               Type of the values inside the {@link java.util.Map}
     * @return a new instance of {@link org.dominokit.jacksonapt.deser.map.MapJsonDeserializer}
     */
    public static <K, V> MapJsonDeserializer<K, V> newInstance(KeyDeserializer<K> keyDeserializer,
                                                               JsonDeserializer<V> valueDeserializer) {
        return new MapJsonDeserializer<K, V>(keyDeserializer, valueDeserializer);
    }

    /**
     * @param keyDeserializer   {@link KeyDeserializer} used to deserialize the keys.
     * @param valueDeserializer {@link JsonDeserializer} used to deserialize the values.
     */
    private MapJsonDeserializer(KeyDeserializer<K> keyDeserializer, JsonDeserializer<V> valueDeserializer) {
        super(keyDeserializer, valueDeserializer);
    }

    /** {@inheritDoc} */
    @Override
    protected Map<K, V> newMap() {
        return new LinkedHashMap<K, V>();
    }
}
