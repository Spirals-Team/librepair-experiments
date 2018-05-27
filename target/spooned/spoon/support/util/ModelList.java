package spoon.support.util;


public abstract class ModelList<T extends spoon.reflect.declaration.CtElement> extends java.util.AbstractList<T> implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private java.util.List<T> list = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    protected ModelList() {
    }

    protected abstract spoon.reflect.declaration.CtElement getOwner();

    protected abstract spoon.reflect.path.CtRole getRole();

    protected abstract int getDefaultCapacity();

    protected void onSizeChanged(int newSize) {
    }

    @java.lang.Override
    public T get(int index) {
        return list.get(index);
    }

    public void set(java.util.Collection<T> elements) {
        this.clear();
        if ((elements != null) && ((elements.isEmpty()) == false)) {
            this.addAll(elements);
        }
    }

    @java.lang.Override
    public int size() {
        return list.size();
    }

    @java.lang.Override
    public T set(int index, T element) {
        T oldElement = list.get(index);
        if (oldElement == element) {
            return oldElement;
        }
        spoon.reflect.declaration.CtElement owner = getOwner();
        ensureModifiableList();
        getModelChangeListener().onListDelete(owner, getRole(), list, index, oldElement);
        spoon.support.util.ModelList.linkToParent(owner, element);
        getModelChangeListener().onListAdd(owner, getRole(), list, index, element);
        list.set(index, element);
        updateModCount();
        return oldElement;
    }

    static void linkToParent(spoon.reflect.declaration.CtElement owner, spoon.reflect.declaration.CtElement element) {
        element.setParent(owner);
    }

    @java.lang.Override
    public boolean contains(java.lang.Object o) {
        return list.contains(o);
    }

    @java.lang.Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @java.lang.Override
    public java.lang.Object[] toArray() {
        return list.toArray();
    }

    @java.lang.Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @java.lang.Override
    public boolean add(T e) {
        if (e == null) {
            return false;
        }
        spoon.reflect.declaration.CtElement owner = getOwner();
        ensureModifiableList();
        spoon.support.util.ModelList.linkToParent(owner, e);
        getModelChangeListener().onListAdd(owner, getRole(), list, e);
        boolean result = list.add(e);
        updateModCount();
        onSizeChanged(list.size());
        return result;
    }

    @java.lang.Override
    public boolean remove(java.lang.Object o) {
        if (list.isEmpty()) {
            return false;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if ((list.get(i)) == o) {
                remove(i);
                return true;
            }
        }
        int idx = list.indexOf(o);
        if (idx >= 0) {
            remove(idx);
            return true;
        }
        return false;
    }

    @java.lang.Override
    public boolean containsAll(java.util.Collection<?> c) {
        return list.containsAll(c);
    }

    @java.lang.Override
    public void clear() {
        getModelChangeListener().onListDeleteAll(getOwner(), getRole(), list, new java.util.ArrayList<>(list));
        list = spoon.support.reflect.declaration.CtElementImpl.emptyList();
        (modCount)++;
        onSizeChanged(list.size());
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        return list.equals(o);
    }

    @java.lang.Override
    public int hashCode() {
        return list.hashCode();
    }

    @java.lang.Override
    public void add(int index, T element) {
        if (element == null) {
            return;
        }
        spoon.reflect.declaration.CtElement owner = getOwner();
        ensureModifiableList();
        spoon.support.util.ModelList.linkToParent(owner, element);
        getModelChangeListener().onListAdd(owner, getRole(), list, index, element);
        list.add(index, element);
        updateModCount();
        onSizeChanged(list.size());
    }

    @java.lang.Override
    public T remove(int index) {
        T oldElement = list.get(index);
        getModelChangeListener().onListDelete(getOwner(), getRole(), list, index, oldElement);
        list.remove(index);
        updateModCount();
        onSizeChanged(list.size());
        return oldElement;
    }

    @java.lang.Override
    public int indexOf(java.lang.Object o) {
        return list.indexOf(o);
    }

    @java.lang.Override
    public int lastIndexOf(java.lang.Object o) {
        return list.lastIndexOf(o);
    }

    private static class InternalList<T> extends java.util.ArrayList<T> {
        InternalList(int initialCapacity) {
            super(initialCapacity);
        }

        int getModCount() {
            return modCount;
        }
    }

    protected void updateModCount() {
        if ((list) instanceof spoon.support.util.ModelList.InternalList) {
            modCount = ((spoon.support.util.ModelList.InternalList) (list)).getModCount();
        }
    }

    private void ensureModifiableList() {
        if ((list) == (spoon.support.reflect.declaration.CtElementImpl.<T>emptyList())) {
            list = new spoon.support.util.ModelList.InternalList<>(getDefaultCapacity());
        }
    }

    private spoon.experimental.modelobs.FineModelChangeListener getModelChangeListener() {
        return getOwner().getFactory().getEnvironment().getModelChangeListener();
    }
}

