package spoon.reflect.code;


public interface CtLambda<T> extends spoon.reflect.code.CtExpression<T> , spoon.reflect.declaration.CtExecutable<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<T> getExpression();

    @spoon.support.DerivedProperty
    <R> spoon.reflect.declaration.CtMethod<R> getOverriddenMethod();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <C extends spoon.reflect.code.CtLambda<T>> C setExpression(spoon.reflect.code.CtExpression<T> expression);

    @java.lang.Override
    spoon.reflect.code.CtLambda<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <T1 extends spoon.reflect.declaration.CtExecutable<T>> T1 setThrownTypes(java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes);
}

