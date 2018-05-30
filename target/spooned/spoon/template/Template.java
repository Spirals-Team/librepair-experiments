package spoon.template;


public interface Template<T extends spoon.reflect.declaration.CtElement> {
    T apply(spoon.reflect.declaration.CtType<?> targetType);
}

