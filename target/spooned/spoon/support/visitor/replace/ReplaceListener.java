package spoon.support.visitor.replace;


public interface ReplaceListener<T extends spoon.reflect.declaration.CtElement> {
    void set(T replace);
}

