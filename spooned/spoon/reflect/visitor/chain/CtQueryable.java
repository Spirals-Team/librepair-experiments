package spoon.reflect.visitor.chain;


public interface CtQueryable {
    <R extends spoon.reflect.declaration.CtElement> spoon.reflect.visitor.chain.CtQuery filterChildren(spoon.reflect.visitor.Filter<R> filter);

    <I, R> spoon.reflect.visitor.chain.CtQuery map(spoon.reflect.visitor.chain.CtFunction<I, R> function);

    <I> spoon.reflect.visitor.chain.CtQuery map(spoon.reflect.visitor.chain.CtConsumableFunction<I> queryStep);
}

