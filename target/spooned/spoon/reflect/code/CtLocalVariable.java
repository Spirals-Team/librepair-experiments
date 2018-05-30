package spoon.reflect.code;


public interface CtLocalVariable<T> extends spoon.reflect.code.CtRHSReceiver<T> , spoon.reflect.code.CtStatement , spoon.reflect.declaration.CtVariable<T> {
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtLocalVariableReference<T> getReference();

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.code.CtExpression<T> getAssignment();

    @java.lang.Override
    spoon.reflect.code.CtLocalVariable<T> clone();
}

