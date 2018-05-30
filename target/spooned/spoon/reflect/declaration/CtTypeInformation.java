package spoon.reflect.declaration;


public interface CtTypeInformation {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.INTERFACE)
    java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getSuperInterfaces();

    @spoon.support.DerivedProperty
    java.lang.String getQualifiedName();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.MODIFIER)
    java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers();

    boolean isPrimitive();

    boolean isAnonymous();

    boolean isLocalType();

    boolean isClass();

    boolean isInterface();

    boolean isEnum();

    boolean isAnnotationType();

    @spoon.support.DerivedProperty
    boolean isGenerics();

    boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> type);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.SUPER_TYPE)
    spoon.reflect.reference.CtTypeReference<?> getSuperclass();

    @spoon.support.DerivedProperty
    java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> getDeclaredFields();

    @spoon.support.DerivedProperty
    java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> getAllFields();

    spoon.reflect.reference.CtFieldReference<?> getDeclaredField(java.lang.String name);

    spoon.reflect.reference.CtFieldReference<?> getDeclaredOrInheritedField(java.lang.String fieldName);

    @spoon.support.DerivedProperty
    java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getDeclaredExecutables();

    @spoon.support.DerivedProperty
    java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getAllExecutables();

    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<?> getTypeErasure();
}

