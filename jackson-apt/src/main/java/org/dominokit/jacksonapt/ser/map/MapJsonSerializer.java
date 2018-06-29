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

package org.dominokit.jacksonapt.ser.map;

import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.ser.map.key.KeySerializer;
import org.dominokit.jacksonapt.stream.JsonWriter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Default {@link JsonSerializer} implementation for {@link Map}.
 *
 * @param <M> Type of the {@link Map}
 * @param <K> Type of the keys inside the {@link Map}
 * @param <V> Type of the values inside the {@link Map}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class MapJsonSerializer<M extends Map<K, V>, K, V> extends JsonSerializer<M> {

    /**
     * <p>newInstance</p>
     *
     * @param keySerializer   {@link KeySerializer} used to serialize the keys.
     * @param valueSerializer {@link JsonSerializer} used to serialize the values.
     * @param <M>             a M object.
     * @return a new instance of {@link MapJsonSerializer}
     */
    public static <M extends Map<?, ?>> MapJsonSerializer<M, ?, ?> newInstance(KeySerializer<?> keySerializer, JsonSerializer<?>
            valueSerializer) {
        return new MapJsonSerializer(keySerializer, valueSerializer);
    }

    protected final KeySerializer<K> keySerializer;

    protected final JsonSerializer<V> valueSerializer;

    /**
     * <p>Constructor for MapJsonSerializer.</p>
     *
     * @param keySerializer   {@link KeySerializer} used to serialize the keys.
     * @param valueSerializer {@link JsonSerializer} used to serialize the values.
     */
    protected MapJsonSerializer(KeySerializer<K> keySerializer, JsonSerializer<V> valueSerializer) {
        if (null == keySerializer) {
            throw new IllegalArgumentException("keySerializer cannot be null");
        }
        if (null == valueSerializer) {
            throw new IllegalArgumentException("valueSerializer cannot be null");
        }
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isEmpty(M value) {
        return null == value || value.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSerialize(JsonWriter writer, M values, JsonSerializationContext ctx, JsonSerializerParameters params) {
        writer.beginObject();

        serializeValues(writer, values, ctx, params);

        writer.endObject();
    }

    /**
     * <p>serializeValues</p>
     *
     * @param writer a {@link org.dominokit.jacksonapt.stream.JsonWriter} object.
     * @param values a M object.
     * @param ctx    a {@link JsonSerializationContext} object.
     * @param params a {@link JsonSerializerParameters} object.
     */
    public void serializeValues(JsonWriter writer, M values, JsonSerializationContext ctx, JsonSerializerParameters params) {
        if (!values.isEmpty()) {
            Map<K, V> map = values;
            if (ctx.isOrderMapEntriesByKeys() && !(values instanceof SortedMap<?, ?>)) {
                map = new TreeMap<K, V>(map);
            }

            if (ctx.isWriteNullMapValues()) {

                for (Entry<K, V> entry : map.entrySet()) {
                    String name = keySerializer.serialize(entry.getKey(), ctx);
                    if (keySerializer.mustBeEscaped(ctx)) {
                        writer.name(name);
                    } else {
                        writer.unescapeName(name);
                    }
                    valueSerializer.serialize(writer, entry.getValue(), ctx, params, true);
                }

            } else {

                for (Entry<K, V> entry : map.entrySet()) {
                    if (null != entry.getValue()) {
                        String name = keySerializer.serialize(entry.getKey(), ctx);
                        if (keySerializer.mustBeEscaped(ctx)) {
                            writer.name(name);
                        } else {
                            writer.unescapeName(name);
                        }
                        valueSerializer.serialize(writer, entry.getValue(), ctx, params, true);
                    }
                }

            }
        }
    }
}
