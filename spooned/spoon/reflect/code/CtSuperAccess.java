package spoon.reflect.code;


public interface CtSuperAccess<T> extends spoon.reflect.code.CtTargetedExpression<T, spoon.reflect.code.CtExpression<?>> , spoon.reflect.code.CtVariableRead<T> {
    @java.lang.Override
    spoon.reflect.code.CtSuperAccess<T> clone();

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<T> getType();
}

