package spoon.reflect.declaration;


public interface CtClass<T extends java.lang.Object> extends spoon.reflect.code.CtStatement , spoon.reflect.declaration.CtType<T> {
    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE)
    java.util.List<spoon.reflect.declaration.CtAnonymousExecutable> getAnonymousExecutables();

    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONSTRUCTOR)
    spoon.reflect.declaration.CtConstructor<T> getConstructor(spoon.reflect.reference.CtTypeReference<?>... parameterTypes);

    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CONSTRUCTOR)
    java.util.Set<spoon.reflect.declaration.CtConstructor<T>> getConstructors();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE)
    <C extends spoon.reflect.declaration.CtClass<T>> C setAnonymousExecutables(java.util.List<spoon.reflect.declaration.CtAnonymousExecutable> e);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE)
    <C extends spoon.reflect.declaration.CtClass<T>> C addAnonymousExecutable(spoon.reflect.declaration.CtAnonymousExecutable e);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE)
    boolean removeAnonymousExecutable(spoon.reflect.declaration.CtAnonymousExecutable e);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONSTRUCTOR)
    <C extends spoon.reflect.declaration.CtClass<T>> C setConstructors(java.util.Set<spoon.reflect.declaration.CtConstructor<T>> constructors);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONSTRUCTOR)
    <C extends spoon.reflect.declaration.CtClass<T>> C addConstructor(spoon.reflect.declaration.CtConstructor<T> constructor);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CONSTRUCTOR)
    void removeConstructor(spoon.reflect.declaration.CtConstructor<T> constructor);

    boolean isAnonymous();

    @java.lang.Override
    spoon.reflect.declaration.CtClass<T> clone();

    T newInstance();
}

