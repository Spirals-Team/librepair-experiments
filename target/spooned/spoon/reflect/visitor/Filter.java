package spoon.reflect.visitor;


public interface Filter<T extends spoon.reflect.declaration.CtElement> {
    boolean matches(T element);
}

