package spoon.reflect.code;


public interface CtVariableAccess<T> extends spoon.reflect.code.CtExpression<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VARIABLE)
    spoon.reflect.reference.CtVariableReference<T> getVariable();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.VARIABLE)
    <C extends spoon.reflect.code.CtVariableAccess<T>> C setVariable(spoon.reflect.reference.CtVariableReference<T> variable);

    @java.lang.Override
    spoon.reflect.code.CtVariableAccess<T> clone();

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<T> getType();
}

