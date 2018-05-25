package spoon.reflect.declaration;


public interface CtField<T> extends spoon.reflect.code.CtRHSReceiver<T> , spoon.reflect.declaration.CtShadowable , spoon.reflect.declaration.CtTypeMember , spoon.reflect.declaration.CtVariable<T> {
    java.lang.String FIELD_SEPARATOR = "#";

    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtFieldReference<T> getReference();

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.code.CtExpression<T> getAssignment();

    @java.lang.Override
    spoon.reflect.declaration.CtField<T> clone();
}

