package spoon.reflect.reference;


public interface CtParameterReference<T> extends spoon.reflect.reference.CtVariableReference<T> {
    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtExecutableReference<?> getDeclaringExecutable();

    @java.lang.Override
    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtParameter<T> getDeclaration();

    @java.lang.Override
    spoon.reflect.reference.CtParameterReference<T> clone();
}

