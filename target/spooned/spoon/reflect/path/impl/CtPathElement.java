package spoon.reflect.path.impl;


public interface CtPathElement<P extends spoon.reflect.declaration.CtElement, T extends spoon.reflect.declaration.CtElement> {
    java.util.Collection<T> getElements(java.util.Collection<P> roots);

    <C extends spoon.reflect.path.impl.CtPathElement<P, T>> C addArgument(java.lang.String key, java.lang.String value);
}

