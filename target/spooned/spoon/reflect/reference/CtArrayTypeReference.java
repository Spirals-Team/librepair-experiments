package spoon.reflect.reference;


public interface CtArrayTypeReference<T> extends spoon.reflect.reference.CtTypeReference<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE)
    spoon.reflect.reference.CtTypeReference<?> getComponentType();

    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<?> getArrayType();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE)
    <C extends spoon.reflect.reference.CtArrayTypeReference<T>> C setComponentType(spoon.reflect.reference.CtTypeReference<?> componentType);

    @spoon.support.DerivedProperty
    int getDimensionCount();

    java.lang.String getSimpleName();

    @java.lang.Override
    spoon.reflect.reference.CtArrayTypeReference<T> clone();
}

