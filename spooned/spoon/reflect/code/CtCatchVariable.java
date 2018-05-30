package spoon.reflect.code;


public interface CtCatchVariable<T> extends spoon.reflect.code.CtCodeElement , spoon.reflect.declaration.CtMultiTypedElement , spoon.reflect.declaration.CtVariable<T> {
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtCatchVariableReference<T> getReference();

    @java.lang.Override
    spoon.reflect.code.CtCatchVariable<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtVariable<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> assignedExpression);

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<T> getType();
}

