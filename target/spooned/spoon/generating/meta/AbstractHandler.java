package spoon.generating.meta;


class AbstractHandler<T, U> implements spoon.reflect.meta.RoleHandler {
    AbstractHandler(spoon.generating.meta.$Role$ role, java.lang.Class<?> targetClass, java.lang.Class<?> valueClass) {
    }

    T castTarget(java.lang.Object e) {
        return null;
    }

    U castValue(java.lang.Object value) {
        return null;
    }

    @java.lang.Override
    public spoon.reflect.path.CtRole getRole() {
        return null;
    }

    @java.lang.Override
    public java.lang.Class<?> getTargetType() {
        return null;
    }

    @java.lang.Override
    public <T, U> U getValue(T element) {
        return null;
    }

    @java.lang.Override
    public <T, U> void setValue(T element, U value) {
    }

    @java.lang.Override
    public java.lang.Class<?> getValueClass() {
        return null;
    }

    @java.lang.Override
    public spoon.reflect.meta.ContainerKind getContainerKind() {
        return null;
    }

    @java.lang.Override
    public <T, U> java.util.Collection<U> asCollection(T element) {
        return null;
    }

    @java.lang.Override
    public <T, U> java.util.Set<U> asSet(T element) {
        return null;
    }

    @java.lang.Override
    public <T, U> java.util.List<U> asList(T element) {
        return null;
    }

    @java.lang.Override
    public <T, U> java.util.Map<java.lang.String, U> asMap(T element) {
        return null;
    }
}

