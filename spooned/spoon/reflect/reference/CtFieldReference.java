package spoon.reflect.reference;


public interface CtFieldReference<T> extends spoon.reflect.reference.CtVariableReference<T> {
    java.lang.reflect.Member getActualField();

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtField<T> getDeclaration();

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtField<T> getFieldDeclaration();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    spoon.reflect.reference.CtTypeReference<?> getDeclaringType();

    java.lang.String getQualifiedName();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_FINAL)
    boolean isFinal();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_STATIC)
    boolean isStatic();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    <C extends spoon.reflect.reference.CtFieldReference<T>> C setDeclaringType(spoon.reflect.reference.CtTypeReference<?> declaringType);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_FINAL)
    <C extends spoon.reflect.reference.CtFieldReference<T>> C setFinal(boolean b);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_STATIC)
    <C extends spoon.reflect.reference.CtFieldReference<T>> C setStatic(boolean b);

    @java.lang.Override
    spoon.reflect.reference.CtFieldReference<T> clone();
}

