package spoon.reflect.reference;


public interface CtIntersectionTypeReference<T> extends spoon.reflect.reference.CtTypeReference<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.BOUND)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getBounds();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.BOUND)
    <C extends spoon.reflect.reference.CtIntersectionTypeReference> C setBounds(java.util.List<spoon.reflect.reference.CtTypeReference<?>> bounds);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.BOUND)
    <C extends spoon.reflect.reference.CtIntersectionTypeReference> C addBound(spoon.reflect.reference.CtTypeReference<?> bound);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.BOUND)
    boolean removeBound(spoon.reflect.reference.CtTypeReference<?> bound);

    @java.lang.Override
    spoon.reflect.reference.CtIntersectionTypeReference<T> clone();
}

