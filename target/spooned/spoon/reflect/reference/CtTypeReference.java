package spoon.reflect.reference;


public interface CtTypeReference<T> extends spoon.reflect.declaration.CtShadowable , spoon.reflect.declaration.CtTypeInformation , spoon.reflect.reference.CtActualTypeContainer , spoon.reflect.reference.CtReference {
    java.lang.String NULL_TYPE_NAME = "<nulltype>";

    @java.lang.Override
    java.lang.String getSimpleName();

    java.lang.Class<T> getActualClass();

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtType<T> getDeclaration();

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtType<T> getTypeDeclaration();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    spoon.reflect.reference.CtTypeReference<?> getDeclaringType();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.PACKAGE_REF)
    spoon.reflect.reference.CtPackageReference getPackage();

    spoon.reflect.reference.CtTypeReference<?> box();

    spoon.reflect.reference.CtTypeReference<?> unbox();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    <C extends spoon.reflect.reference.CtTypeReference<T>> C setDeclaringType(spoon.reflect.reference.CtTypeReference<?> type);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.PACKAGE_REF)
    <C extends spoon.reflect.reference.CtTypeReference<T>> C setPackage(spoon.reflect.reference.CtPackageReference pack);

    spoon.reflect.reference.CtIntersectionTypeReference<T> asCtIntersectionTypeReference();

    @java.lang.Override
    spoon.reflect.reference.CtTypeReference<T> clone();

    @java.lang.Override
    @spoon.support.DerivedProperty
    java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getSuperInterfaces();

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<?> getSuperclass();

    boolean canAccess(spoon.reflect.reference.CtTypeReference<?> type);

    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<?> getTopLevelType();

    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<?> getAccessType();

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtTypeParameter getTypeParameterDeclaration();
}

