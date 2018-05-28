package spoon.reflect.meta.impl;


abstract class SingleHandler<T, U> extends spoon.reflect.meta.impl.AbstractRoleHandler<T, U, U> {
    protected SingleHandler(spoon.reflect.path.CtRole role, java.lang.Class<T> targetType, java.lang.Class<?> valueClass) {
        super(role, targetType, valueClass);
    }

    @java.lang.Override
    public spoon.reflect.meta.ContainerKind getContainerKind() {
        return spoon.reflect.meta.ContainerKind.SINGLE;
    }

    public <W, X> java.util.Collection<X> asCollection(W element) {
        return asList(element);
    }

    public <W, X> java.util.List<X> asList(W e) {
        return new java.util.AbstractList<X>() {
            T element = castTarget(e);

            boolean hasValue = (spoon.reflect.meta.impl.SingleHandler.this.getValue(element)) != null;

            @java.lang.Override
            public int size() {
                return hasValue ? 1 : 0;
            }

            @java.lang.SuppressWarnings("unchecked")
            @java.lang.Override
            public X get(int index) {
                if ((index < 0) || (index >= (size()))) {
                    throw new java.lang.IndexOutOfBoundsException(((("Index: " + index) + ", Size: ") + (size())));
                }
                return ((X) (spoon.reflect.meta.impl.SingleHandler.this.getValue(element)));
            }

            @java.lang.Override
            public X set(int index, X value) {
                if ((index < 0) || (index >= (size()))) {
                    throw new java.lang.IndexOutOfBoundsException(((("Index: " + index) + ", Size: ") + (size())));
                }
                X oldValue = get(0);
                spoon.reflect.meta.impl.SingleHandler.this.setValue(element, value);
                return ((X) (oldValue));
            }

            @java.lang.Override
            public boolean add(X value) {
                if (hasValue) {
                    throw new spoon.SpoonException("Single value attribute cannot have more then one value");
                }
                spoon.reflect.meta.impl.SingleHandler.this.setValue(element, value);
                hasValue = true;
                return true;
            }

            @java.lang.Override
            public X remove(int index) {
                if ((index < 0) || (index >= (size()))) {
                    throw new java.lang.IndexOutOfBoundsException(((("Index: " + index) + ", Size: ") + (size())));
                }
                X oldValue = get(0);
                if (oldValue != null) {
                    spoon.reflect.meta.impl.SingleHandler.this.setValue(element, null);
                }
                hasValue = false;
                return oldValue;
            }

            @java.lang.Override
            public boolean remove(java.lang.Object value) {
                if ((hasValue) == false) {
                    return false;
                }
                X oldValue = get(0);
                if (equals(oldValue, value)) {
                    if (oldValue != null) {
                        spoon.reflect.meta.impl.SingleHandler.this.setValue(element, null);
                    }
                    hasValue = false;
                    return true;
                }
                return false;
            }

            private boolean equals(java.lang.Object v1, java.lang.Object v2) {
                if (v1 == v2) {
                    return true;
                }
                if (v1 == null) {
                    return false;
                }
                return v1.equals(v2);
            }
        };
    }

    public <W, X> java.util.Set<X> asSet(W element) {
        return java.util.Collections.<X>singleton(getValue(element));
    }
}

