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

package org.dominokit.jacksonapt.deser.bean;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains type deserialization informations
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class TypeDeserializationInfo<T> {

    /**
     * Inclusion mechanism
     */
    private final As include;

    /**
     * Name of the property containing information about the type
     */
    private final String propertyName;

    private final Map<String, Class<? extends T>> typeInfoToClass;

    /**
     * <p>Constructor for TypeDeserializationInfo.</p>
     *
     * @param include      a {@link As} object.
     * @param propertyName a {@link String} object.
     */
    public TypeDeserializationInfo(As include, String propertyName) {
        this.include = include;
        this.propertyName = propertyName;
        this.typeInfoToClass = new HashMap<String, Class<? extends T>>();
    }

    /**
     * <p>addTypeInfo</p>
     *
     * @param clazz    a {@link Class} object.
     * @param typeInfo a {@link String} object.
     * @param <S>      the type
     * @return a {@link TypeDeserializationInfo} object.
     */
    public <S extends T> TypeDeserializationInfo<T> addTypeInfo(Class<S> clazz, String typeInfo) {
        typeInfoToClass.put(typeInfo, clazz);
        return this;
    }

    /**
     * <p>Getter for the field <code>include</code>.</p>
     *
     * @return a {@link As} object.
     */
    public As getInclude() {
        return include;
    }

    /**
     * <p>Getter for the field <code>propertyName</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * <p>getTypeClass</p>
     *
     * @param typeInfo a {@link String} object.
     * @return a {@link Class} object.
     */
    public Class<? extends T> getTypeClass(String typeInfo) {
        return typeInfoToClass.get(typeInfo);
    }
}
