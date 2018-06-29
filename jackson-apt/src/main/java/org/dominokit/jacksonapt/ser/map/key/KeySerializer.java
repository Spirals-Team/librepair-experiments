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

package org.dominokit.jacksonapt.ser.map.key;

import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.exception.JsonSerializationException;

/**
 * Base class for all the {@link java.util.Map} key serializer. It handles null values and exceptions. The rest is delegated to implementations.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class KeySerializer<T> {

    /**
     * <p>mustBeEscaped</p>
     *
     * @param ctx Context for the full serialization process
     * @return true if the serialized key must be escaped
     */
    public boolean mustBeEscaped(JsonSerializationContext ctx) {
        return true;
    }

    /**
     * Serializes an object into a {@link java.lang.String} to use as map's key.
     *
     * @param value Object to serialize
     * @param ctx   Context for the full serialization process
     * @return the key
     * @throws org.dominokit.jacksonapt.exception.JsonSerializationException if an error occurs during the serialization
     */
    public String serialize(T value, JsonSerializationContext ctx) throws JsonSerializationException {
        if (null == value) {
            return null;
        }
        return doSerialize(value, ctx);
    }

    /**
     * Serializes a non-null object into a {@link java.lang.String} to use as map's key.
     *
     * @param value Object to serialize
     * @param ctx   Context for the full serialization process
     * @return the key
     */
    protected abstract String doSerialize(T value, JsonSerializationContext ctx);
}
