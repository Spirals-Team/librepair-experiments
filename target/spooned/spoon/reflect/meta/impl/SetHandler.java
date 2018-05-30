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
 * implementation of {@link RoleHandler}, which handles attributes of type Set&lt;V&gt;
 *
 * @param <T>
 * 		the type of node whose attribute has to be manipulated
 * @param <V>
 * 		the type of item value of the attribute
 */
abstract class SetHandler<T, V> extends spoon.reflect.meta.impl.AbstractRoleHandler<T, java.util.Set<V>, V> {
    protected SetHandler(spoon.reflect.path.CtRole role, java.lang.Class<T> targetType, java.lang.Class<?> valueClass) {
        super(role, targetType, valueClass);
    }

    @java.lang.Override
    public spoon.reflect.meta.ContainerKind getContainerKind() {
        return spoon.reflect.meta.ContainerKind.SET;
    }

    @java.lang.Override
    protected java.util.Set<V> castValue(java.lang.Object value) {
        java.util.Set<V> set = super.castValue(value);
        // check that each item has expected class
        checkItemsClass(set);
        return set;
    }

    @java.lang.Override
    public <W, X> java.util.Collection<X> asCollection(W element) {
        return asSet(element);
    }

    @java.lang.Override
    public <W, X> java.util.Set<X> asSet(W e) {
        return new java.util.AbstractSet<X>() {
            T element = castTarget(e);

            @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
            @java.lang.Override
            public java.util.Iterator<X> iterator() {
                return ((java.util.Iterator) (spoon.reflect.meta.impl.SetHandler.this.iterator(element)));
            }

            @java.lang.Override
            public int size() {
                return spoon.reflect.meta.impl.SetHandler.this.size(element);
            }

            @java.lang.Override
            public boolean contains(java.lang.Object o) {
                return spoon.reflect.meta.impl.SetHandler.this.contains(element, o);
            }

            @java.lang.Override
            public boolean add(X value) {
                return spoon.reflect.meta.impl.SetHandler.this.add(element, castItemValue(value));
            }

            @java.lang.Override
            public boolean remove(java.lang.Object value) {
                return spoon.reflect.meta.impl.SetHandler.this.remove(element, value);
            }
        };
    }

    protected boolean remove(T element, java.lang.Object value) {
        java.util.Set<V> values = new java.util.HashSet<>(this.<T, java.util.Set<V>>getValue(element));
        boolean ret = values.remove(value);
        if (ret) {
            setValue(element, values);
        }
        return false;
    }

    protected boolean add(T element, V value) {
        java.util.Set<V> values = new java.util.HashSet<>(this.<T, java.util.Set<V>>getValue(element));
        boolean ret = values.add(value);
        if (ret) {
            setValue(element, values);
        }
        return ret;
    }

    protected boolean contains(T element, java.lang.Object o) {
        return this.<T, java.util.Set<V>>getValue(element).contains(o);
    }

    protected int size(T element) {
        return this.<T, java.util.Set<V>>getValue(element).size();
    }

    protected java.util.Iterator<V> iterator(T element) {
        return this.<T, java.util.Set<V>>getValue(element).iterator();
    }
}

