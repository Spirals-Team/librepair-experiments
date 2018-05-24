package spoon.reflect.meta.impl;


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

