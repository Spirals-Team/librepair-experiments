package spoon.test.generics.testclasses2;


public interface LikeCtClass<T extends java.lang.Object> {
    java.util.Set<spoon.test.generics.testclasses2.AnType<T>> getConstructors();

    <C extends spoon.test.generics.testclasses2.LikeCtClass<T>> C setConstructors(java.util.Set<spoon.test.generics.testclasses2.AnType<T>> constructors);
}

