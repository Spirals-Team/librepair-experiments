package spoon.reflect.declaration;


public interface CtAnnotationType<T extends java.lang.annotation.Annotation> extends spoon.reflect.declaration.CtType<T> {
    @spoon.support.DerivedProperty
    java.util.Set<spoon.reflect.declaration.CtAnnotationMethod<?>> getAnnotationMethods();

    @java.lang.Override
    <M, C extends spoon.reflect.declaration.CtType<T>> C addMethod(spoon.reflect.declaration.CtMethod<M> method);

    @java.lang.Override
    <C extends spoon.reflect.declaration.CtType<T>> C setMethods(java.util.Set<spoon.reflect.declaration.CtMethod<?>> methods);

    @java.lang.Override
    spoon.reflect.declaration.CtAnnotationType<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtFormalTypeDeclarer> T setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperInterfaces(java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass);
}

