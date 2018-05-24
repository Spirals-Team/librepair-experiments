package spoon.reflect.declaration;


public interface CtExecutable<R> extends spoon.reflect.code.CtBodyHolder , spoon.reflect.declaration.CtNamedElement , spoon.reflect.declaration.CtTypedElement<R> {
    java.lang.String EXECUTABLE_SEPARATOR = "#";

    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtExecutableReference<R> getReference();

    @java.lang.Override
    spoon.reflect.code.CtBlock<R> getBody();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.PARAMETER)
    java.util.List<spoon.reflect.declaration.CtParameter<?>> getParameters();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.PARAMETER)
    <T extends spoon.reflect.declaration.CtExecutable<R>> T setParameters(java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.PARAMETER)
    <T extends spoon.reflect.declaration.CtExecutable<R>> T addParameter(spoon.reflect.declaration.CtParameter<?> parameter);

    boolean removeParameter(spoon.reflect.declaration.CtParameter<?> parameter);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.THROWN)
    java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> getThrownTypes();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.THROWN)
    <T extends spoon.reflect.declaration.CtExecutable<R>> T setThrownTypes(java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.THROWN)
    <T extends spoon.reflect.declaration.CtExecutable<R>> T addThrownType(spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> throwType);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.THROWN)
    boolean removeThrownType(spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable> throwType);

    java.lang.String getSignature();

    @java.lang.Override
    spoon.reflect.declaration.CtExecutable<R> clone();
}

