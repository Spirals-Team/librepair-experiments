package spoon.reflect.code;


public interface CtAnnotationFieldAccess<T> extends spoon.reflect.code.CtTargetedExpression<T, spoon.reflect.code.CtExpression<?>> , spoon.reflect.code.CtVariableRead<T> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.VARIABLE)
    spoon.reflect.reference.CtFieldReference<T> getVariable();

    @java.lang.Override
    spoon.reflect.code.CtAnnotationFieldAccess<T> clone();
}

