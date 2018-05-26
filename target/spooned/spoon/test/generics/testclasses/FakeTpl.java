package spoon.test.generics.testclasses;


public interface FakeTpl<T extends spoon.reflect.declaration.CtElement> {
    T apply(spoon.reflect.declaration.CtType<? extends spoon.reflect.declaration.CtNamedElement> targetType);

    java.lang.String test(spoon.reflect.declaration.CtType<?> something, int i, T bidule);
}

