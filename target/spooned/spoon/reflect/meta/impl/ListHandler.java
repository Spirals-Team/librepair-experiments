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
 * implementation of {@link RoleHandler}, which handles attributes of type List&lt;V&gt;
 *
 * @param <T>
 * 		the type of node whose attribute has to be manipulated
 * @param <V>
 * 		the type of item value of the attribute
 */
abstract class ListHandler<T, V> extends spoon.reflect.meta.impl.AbstractRoleHandler<T, java.util.List<V>, V> {
    protected ListHandler(spoon.reflect.path.CtRole role, java.lang.Class<T> targetType, java.lang.Class<?> valueClass) {
        super(role, targetType, valueClass);
    }

    @java.lang.Override
    public spoon.reflect.meta.ContainerKind getContainerKind() {
        return spoon.reflect.meta.ContainerKind.LIST;
    }

    @java.lang.Override
    protected java.util.List<V> castValue(java.lang.Object value) {
        java.util.List<V> list = super.castValue(value);
        // check that each item has expected class
        checkItemsClass(list);
        return list;
    }

    @java.lang.Override
    public <W, X> java.util.Collection<X> asCollection(W element) {
        return asList(element);
    }

    @java.lang.Override
    public <W, X> java.util.List<X> asList(W e) {
        return new java.util.AbstractList<X>() {
            T element = castTarget(e);

            @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
            @java.lang.Override
            public java.util.Iterator<X> iterator() {
                return ((java.util.Iterator) (spoon.reflect.meta.impl.ListHandler.this.iterator(element)));
            }

            @java.lang.Override
            public int size() {
                return spoon.reflect.meta.impl.ListHandler.this.size(element);
            }

            @java.lang.SuppressWarnings("unchecked")
            @java.lang.Override
            public X get(int index) {
                return ((X) (spoon.reflect.meta.impl.ListHandler.this.get(element, index)));
            }

            @java.lang.Override
            public X set(int index, X value) {
                return ((X) (spoon.reflect.meta.impl.ListHandler.this.set(element, index, castItemValue(value))));
            }

            @java.lang.Override
            public X remove(int index) {
                return ((X) (spoon.reflect.meta.impl.ListHandler.this.remove(element, index)));
            }

            @java.lang.Override
            public boolean add(X value) {
                return spoon.reflect.meta.impl.ListHandler.this.add(element, castItemValue(value));
            }

            @java.lang.Override
            public boolean remove(java.lang.Object value) {
                return spoon.reflect.meta.impl.ListHandler.this.remove(element, value);
            }
        };
    }

    protected boolean remove(T element, java.lang.Object value) {
        java.util.List<V> values = new java.util.ArrayList<>(this.<T, java.util.List<V>>getValue(element));
        boolean ret = values.remove(value);
        if (ret) {
            setValue(element, values);
        }
        return ret;
    }

    protected V remove(T element, int index) {
        java.util.List<V> values = new java.util.ArrayList<>(this.<T, java.util.List<V>>getValue(element));
        V ret = values.remove(index);
        setValue(element, values);
        return ret;
    }

    protected boolean add(T element, V value) {
        java.util.List<V> values = new java.util.ArrayList<>(this.<T, java.util.List<V>>getValue(element));
        boolean ret = values.add(value);
        setValue(element, values);
        return ret;
    }

    protected V get(T element, int index) {
        return this.<T, java.util.List<V>>getValue(element).get(index);
    }

    protected V set(T element, int index, V value) {
        java.util.List<V> values = new java.util.ArrayList<>(this.<T, java.util.List<V>>getValue(element));
        V ret = values.set(index, value);
        setValue(element, values);
        return ret;
    }

    protected int size(T element) {
        return this.<T, java.util.List<V>>getValue(element).size();
    }

    protected java.util.Iterator<V> iterator(T element) {
        return this.<T, java.util.List<V>>getValue(element).iterator();
    }
}

