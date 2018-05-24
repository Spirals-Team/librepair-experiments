package spoon.test.generics.testclasses2;


public class LikeCtClassImpl<T extends java.lang.Object> implements spoon.test.generics.testclasses2.LikeCtClass<T> {
    public java.util.Set<spoon.test.generics.testclasses2.AnType<T>> getConstructors() {
        return null;
    }

    public <C extends spoon.test.generics.testclasses2.LikeCtClass<T>> C setConstructors(java.util.Set<spoon.test.generics.testclasses2.AnType<T>> constructors) {
        return ((C) (this));
    }
}

