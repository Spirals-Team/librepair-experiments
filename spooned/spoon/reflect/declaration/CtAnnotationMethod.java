package spoon.reflect.declaration;


public interface CtAnnotationMethod<T> extends spoon.reflect.declaration.CtMethod<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)
    spoon.reflect.code.CtExpression<T> getDefaultExpression();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)
    <C extends spoon.reflect.declaration.CtAnnotationMethod<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> assignedExpression);

    @java.lang.Override
    spoon.reflect.declaration.CtAnnotationMethod<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T1 extends spoon.reflect.code.CtBodyHolder> T1 setBody(spoon.reflect.code.CtStatement body);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T1 extends spoon.reflect.declaration.CtExecutable<T>> T1 setThrownTypes(java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T extends spoon.reflect.declaration.CtFormalTypeDeclarer> T setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters);

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T1 extends spoon.reflect.declaration.CtExecutable<T>> T1 setParameters(java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters);
}

