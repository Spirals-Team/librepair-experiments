package spoon.reflect.reference;


public interface CtCatchVariableReference<T> extends spoon.reflect.reference.CtVariableReference<T> {
    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.code.CtCatchVariable<T> getDeclaration();

    @java.lang.Override
    spoon.reflect.reference.CtCatchVariableReference<T> clone();
}

