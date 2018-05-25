package spoon.reflect.code;


public interface CtFieldAccess<T> extends spoon.reflect.code.CtTargetedExpression<T, spoon.reflect.code.CtExpression<?>> , spoon.reflect.code.CtVariableAccess<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VARIABLE)
    spoon.reflect.reference.CtFieldReference<T> getVariable();

    @java.lang.Override
    spoon.reflect.code.CtFieldAccess<T> clone();
}

