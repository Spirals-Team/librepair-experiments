package spoon.reflect.meta.impl;


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

