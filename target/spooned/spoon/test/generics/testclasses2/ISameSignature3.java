package spoon.test.generics.testclasses2;


interface ISameSignature3<T> {
    <V, L extends V, K extends T> void visitCtConditional(final spoon.reflect.code.CtConditional<V> conditional, L l, K k);
}

