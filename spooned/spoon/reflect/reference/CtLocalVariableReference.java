package spoon.reflect.reference;


public interface CtLocalVariableReference<T> extends spoon.reflect.reference.CtVariableReference<T> {
    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.code.CtLocalVariable<T> getDeclaration();

    @java.lang.Override
    spoon.reflect.reference.CtLocalVariableReference<T> clone();
}

