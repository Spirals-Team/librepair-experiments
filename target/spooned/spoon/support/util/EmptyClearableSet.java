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
package spoon.support.util;


public final class EmptyClearableSet<E> extends java.util.AbstractSet<E> implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    private static final spoon.support.util.EmptyClearableSet<java.lang.Object> EMPTY_SET = new spoon.support.util.EmptyClearableSet<>();

    public static <T> java.util.Set<T> instance() {
        return ((java.util.Set<T>) (spoon.support.util.EmptyClearableSet.EMPTY_SET));
    }

    private EmptyClearableSet() {
    }

    @java.lang.Override
    public void clear() {
        // do nothing
    }

    @java.lang.Override
    public java.util.Iterator<E> iterator() {
        return spoon.support.util.EmptyIterator.instance();
    }

    @java.lang.Override
    public int size() {
        return 0;
    }

    @java.lang.Override
    public boolean isEmpty() {
        return true;
    }

    @java.lang.Override
    public boolean contains(java.lang.Object obj) {
        return false;
    }

    @java.lang.Override
    public boolean containsAll(java.util.Collection<?> c) {
        return c.isEmpty();
    }

    @java.lang.Override
    public java.lang.Object[] toArray() {
        return new java.lang.Object[0];
    }

    @java.lang.Override
    public <T> T[] toArray(T[] a) {
        if ((a.length) > 0) {
            a[0] = null;
        }
        return a;
    }

    // Preserves singleton property
    private java.lang.Object readResolve() {
        return spoon.support.util.EmptyClearableSet.EMPTY_SET;
    }
}

