package spoon.reflect.declaration;


public interface CtInterface<T> extends spoon.reflect.declaration.CtType<T> {
    @java.lang.Override
    spoon.reflect.declaration.CtInterface<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass);
}

