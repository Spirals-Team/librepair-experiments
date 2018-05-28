package spoon.reflect.meta;


public interface RoleHandler {
    <T, U> U getValue(T element);

    <T, U> void setValue(T element, U value);

    spoon.reflect.path.CtRole getRole();

    java.lang.Class<?> getTargetType();

    java.lang.Class<?> getValueClass();

    spoon.reflect.meta.ContainerKind getContainerKind();

    <T, U> java.util.Collection<U> asCollection(T element);

    <T, U> java.util.Set<U> asSet(T element);

    <T, U> java.util.List<U> asList(T element);

    <T, U> java.util.Map<java.lang.String, U> asMap(T element);
}

