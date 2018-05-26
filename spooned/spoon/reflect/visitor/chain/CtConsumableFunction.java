package spoon.reflect.visitor.chain;


public interface CtConsumableFunction<T> {
    void apply(T input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer);
}

