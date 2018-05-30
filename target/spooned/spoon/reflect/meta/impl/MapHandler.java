/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.meta.impl;


/**
 * implementation of {@link RoleHandler}, which handles attributes of type Map&lt;String, V&gt;
 *
 * @param <T>
 * 		the type of node whose attribute has to be manipulated
 * @param <V>
 * 		the type of item value of the attribute
 */
abstract class MapHandler<T, V> extends spoon.reflect.meta.impl.AbstractRoleHandler<T, java.util.Map<java.lang.String, V>, V> {
    protected MapHandler(spoon.reflect.path.CtRole role, java.lang.Class<T> targetType, java.lang.Class<?> valueClass) {
        super(role, targetType, valueClass);
    }

    @java.lang.Override
    public spoon.reflect.meta.ContainerKind getContainerKind() {
        return spoon.reflect.meta.ContainerKind.MAP;
    }

    @java.lang.Override
    protected java.util.Map<java.lang.String, V> castValue(java.lang.Object value) {
        java.util.Map<java.lang.String, V> map = super.castValue(value);
        // check that each item has expected class
        checkItemsClass(map.values());
        return map;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
    public <W, X> java.util.Collection<X> asCollection(W element) {
        return ((java.util.Collection) (asMap(element).values()));
    }

    @java.lang.Override
    public <W, X> java.util.Map<java.lang.String, X> asMap(W e) {
        // TODO Auto-generated method stub
        return new java.util.AbstractMap<java.lang.String, X>() {
            T element = castTarget(e);

            @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
            @java.lang.Override
            public java.util.Set<java.util.Map.Entry<java.lang.String, X>> entrySet() {
                return ((java.util.Set) (spoon.reflect.meta.impl.MapHandler.this.entrySet(element)));
            }

            @java.lang.SuppressWarnings("unchecked")
            @java.lang.Override
            public X get(java.lang.Object key) {
                return ((X) (spoon.reflect.meta.impl.MapHandler.this.get(element, key)));
            }

            @java.lang.SuppressWarnings("unchecked")
            @java.lang.Override
            public X put(java.lang.String key, X value) {
                return ((X) (spoon.reflect.meta.impl.MapHandler.this.put(element, key, castItemValue(value))));
            }
        };
    }

    protected V get(T element, java.lang.Object key) {
        return this.<T, java.util.Map<java.lang.String, V>>getValue(element).get(key);
    }

    protected V put(T element, java.lang.String key, V value) {
        java.util.Map<java.lang.String, V> values = new java.util.LinkedHashMap<>(this.<T, java.util.Map<java.lang.String, V>>getValue(element));
        V ret = values.put(key, value);
        setValue(element, values);
        return ret;
    }

    protected java.util.Set<java.util.Map.Entry<java.lang.String, V>> entrySet(T element) {
        return this.<T, java.util.Map<java.lang.String, V>>getValue(element).entrySet();
    }
}

