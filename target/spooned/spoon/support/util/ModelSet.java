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


/**
 * The implementation of the {@link Set}, which is used by Spoon model objects.
 * It assures:
 * 1) each inserted {@link CtElement} gets assigned correct parent
 * 2) each change is reported in {@link FineModelChangeListener}
 */
public abstract class ModelSet<T extends spoon.reflect.declaration.CtElement> extends java.util.AbstractSet<T> implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private final java.util.Set<T> set;

    protected ModelSet(java.util.Comparator<? super spoon.reflect.declaration.CtElement> comparator) {
        set = new java.util.TreeSet<>(comparator);
    }

    protected abstract spoon.reflect.declaration.CtElement getOwner();

    protected abstract spoon.reflect.path.CtRole getRole();

    protected void onSizeChanged(int newSize) {
    }

    @java.lang.Override
    public int size() {
        return set.size();
    }

    @java.lang.Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @java.lang.Override
    public boolean contains(java.lang.Object o) {
        return set.contains(o);
    }

    @java.lang.Override
    public java.lang.Object[] toArray() {
        return set.toArray();
    }

    @java.lang.Override
    public <T> T[] toArray(T[] a) {
        return set.toArray(a);
    }

    @java.lang.Override
    public boolean add(T e) {
        if ((e == null) || (set.contains(e))) {
            return false;
        }
        spoon.reflect.declaration.CtElement owner = getOwner();
        spoon.support.util.ModelList.linkToParent(owner, e);
        getModelChangeListener().onSetAdd(owner, getRole(), set, e);
        set.add(e);
        return true;
    }

    @java.lang.Override
    public boolean remove(java.lang.Object o) {
        if ((set.contains(o)) == false) {
            return false;
        }
        @java.lang.SuppressWarnings("unchecked")
        T e = ((T) (o));
        getModelChangeListener().onSetDelete(getOwner(), getRole(), set, e);
        if ((set.remove(o)) == false) {
            throw new spoon.SpoonException("Element was contained in the Set, but Set#remove returned false. Not removed??");
        }
        return true;
    }

    @java.lang.Override
    public boolean containsAll(java.util.Collection<?> c) {
        return set.containsAll(c);
    }

    @java.lang.Override
    public void clear() {
        if (set.isEmpty()) {
            return;
        }
        getModelChangeListener().onSetDeleteAll(getOwner(), getRole(), set, new java.util.LinkedHashSet<T>(set));
        set.clear();
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        return set.equals(o);
    }

    @java.lang.Override
    public int hashCode() {
        return set.hashCode();
    }

    @java.lang.Override
    public java.util.Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements java.util.Iterator<T> {
        final java.util.Iterator<T> delegate;

        T lastReturned = null;

        Itr() {
            delegate = set.iterator();
        }

        @java.lang.Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        @java.lang.Override
        public T next() {
            lastReturned = delegate.next();
            return lastReturned;
        }

        @java.lang.Override
        public void remove() {
            spoon.support.util.ModelSet.this.remove(lastReturned);
        }
    }

    private spoon.experimental.modelobs.FineModelChangeListener getModelChangeListener() {
        return getOwner().getFactory().getEnvironment().getModelChangeListener();
    }

    public void set(java.util.Collection<T> elements) {
        // TODO the best would be to detect added/removed statements and to fire modifications only for them
        this.clear();
        if ((elements != null) && ((elements.isEmpty()) == false)) {
            this.addAll(elements);
        }
    }
}

