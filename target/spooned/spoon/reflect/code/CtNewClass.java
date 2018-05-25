package spoon.reflect.code;


public interface CtNewClass<T> extends spoon.reflect.code.CtConstructorCall<T> {
    @java.lang.Override
    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getActualTypeArguments();

    @java.lang.Override
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    <T extends spoon.reflect.reference.CtActualTypeContainer> T setActualTypeArguments(java.util.List<? extends spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments);

    @java.lang.Override
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    <T extends spoon.reflect.reference.CtActualTypeContainer> T addActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    spoon.reflect.declaration.CtClass<?> getAnonymousClass();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    <N extends spoon.reflect.code.CtNewClass> N setAnonymousClass(spoon.reflect.declaration.CtClass<?> anonymousClass);

    @java.lang.Override
    spoon.reflect.code.CtNewClass<T> clone();
}

