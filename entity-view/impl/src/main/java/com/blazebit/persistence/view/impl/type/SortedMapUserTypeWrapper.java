/*
 * Copyright 2014 - 2018 Blazebit.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blazebit.persistence.view.impl.type;

import com.blazebit.persistence.view.spi.type.BasicUserType;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Christian Beikov
 * @since 1.2.0
 */
public class SortedMapUserTypeWrapper<K, V> extends AbstractMapUserTypeWrapper<Map<K, V>, K, V> {

    private final Comparator<K> comparator;

    public SortedMapUserTypeWrapper(BasicUserType<K> keyUserType, BasicUserType<V> elementUserType, Comparator<K> comparator) {
        super(keyUserType, elementUserType);
        this.comparator = comparator;
    }

    @Override
    protected Map<K, V> createCollection(int size) {
        return new TreeMap<>(comparator);
    }
}
