package spoon.reflect.meta.impl;


abstract class AbstractRoleHandler<T, U, V> implements spoon.reflect.meta.RoleHandler {
    private final spoon.reflect.path.CtRole role;

    private final java.lang.Class<T> targetClass;

    private final java.lang.Class<V> valueClass;

    @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
    protected AbstractRoleHandler(spoon.reflect.path.CtRole role, java.lang.Class<T> targetType, java.lang.Class<?> valueType) {
        this.role = role;
        this.targetClass = targetType;
        this.valueClass = ((java.lang.Class) (valueType));
    }

    @java.lang.Override
    public spoon.reflect.path.CtRole getRole() {
        return role;
    }

    @java.lang.Override
    public java.lang.Class<?> getTargetType() {
        return targetClass;
    }

    @java.lang.SuppressWarnings("unchecked")
    protected T castTarget(java.lang.Object element) {
        return ((T) (element));
    }

    @java.lang.SuppressWarnings("unchecked")
    protected U castValue(java.lang.Object value) {
        return ((U) (value));
    }

    protected void checkItemsClass(java.lang.Iterable<?> iterable) {
        for (java.lang.Object value : iterable) {
            castItemValue(value);
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    protected V castItemValue(java.lang.Object value) {
        if ((value != null) && ((valueClass.isInstance(value)) == false)) {
            throw new java.lang.ClassCastException((((value.getClass().getName()) + " cannot be cast to ") + (valueClass.getName())));
        }
        return ((V) (value));
    }

    public <W, X> void setValue(W element, X value) {
        throw new spoon.SpoonException(((("Setting of CtRole." + (role.name())) + " is not supported for ") + (element.getClass().getSimpleName())));
    }

    @java.lang.Override
    public java.lang.Class<?> getValueClass() {
        return valueClass;
    }

    @java.lang.Override
    public <W, X> java.util.List<X> asList(W element) {
        throw new spoon.SpoonException(((("The value of CtRole." + (getRole().name())) + " cannot be adapted to List for ") + (element.getClass().getSimpleName())));
    }

    @java.lang.Override
    public <W, X> java.util.Set<X> asSet(W element) {
        throw new spoon.SpoonException(((("The value of CtRole." + (getRole().name())) + " cannot be adapted to Set for ") + (element.getClass().getSimpleName())));
    }

    @java.lang.Override
    public <W, X> java.util.Map<java.lang.String, X> asMap(W element) {
        throw new spoon.SpoonException(((("The value of CtRole." + (getRole().name())) + " cannot be adapted to Map for ") + (element.getClass().getSimpleName())));
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((getTargetType().getName()) + "#") + (getRole().getCamelCaseName());
    }
}

