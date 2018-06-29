/*
 * Copyright 2014 Nicolas Morel
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

package org.dominokit.jacksonapt.deser.bean;

import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.stream.JsonReader;

/**
 * Deserializes a bean's property
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AnySetterDeserializer<T, V> extends HasDeserializerAndParameters<V, JsonDeserializer<V>> {

    /**
     * Deserializes the property defined for this instance.
     *
     * @param reader       reader
     * @param bean         bean to set the deserialized property to
     * @param propertyName name of the property
     * @param ctx          context of the deserialization process
     */
    public void deserialize(JsonReader reader, T bean, String propertyName, JsonDeserializationContext ctx) {
        setValue(bean, propertyName, deserialize(reader, ctx), ctx);
    }

    /**
     * <p>setValue</p>
     *
     * @param bean         a T object.
     * @param propertyName a {@link java.lang.String} object.
     * @param value        a V object.
     * @param ctx          a {@link org.dominokit.jacksonapt.JsonDeserializationContext} object.
     */
    public abstract void setValue(T bean, String propertyName, V value, JsonDeserializationContext ctx);
}

