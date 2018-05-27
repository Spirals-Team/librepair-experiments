package spoon.reflect.visitor.chain;


public interface CtFunction<T, R> {
    R apply(T input);
}

