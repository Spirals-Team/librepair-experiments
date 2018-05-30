package spoon.reflect.declaration;


public interface CtParameter<T> extends spoon.reflect.declaration.CtShadowable , spoon.reflect.declaration.CtVariable<T> {
    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtExecutable<?> getParent();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_VARARGS)
    boolean isVarArgs();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_VARARGS)
    <C extends spoon.reflect.declaration.CtParameter<T>> C setVarArgs(boolean varArgs);

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtParameterReference<T> getReference();

    @java.lang.Override
    spoon.reflect.declaration.CtParameter<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtVariable<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> assignedExpression);
}

