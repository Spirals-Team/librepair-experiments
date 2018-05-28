package spoon.reflect.visitor.chain;


public interface CtQuery extends spoon.reflect.visitor.chain.CtQueryable {
    @java.lang.Override
    <R extends spoon.reflect.declaration.CtElement> spoon.reflect.visitor.chain.CtQuery filterChildren(spoon.reflect.visitor.Filter<R> filter);

    <R extends spoon.reflect.declaration.CtElement> spoon.reflect.visitor.chain.CtQuery select(spoon.reflect.visitor.Filter<R> filter);

    @java.lang.Override
    <I, R> spoon.reflect.visitor.chain.CtQuery map(spoon.reflect.visitor.chain.CtFunction<I, R> function);

    <T extends spoon.reflect.visitor.chain.CtQuery> T setInput(java.lang.Object... input);

    <R> void forEach(spoon.reflect.visitor.chain.CtConsumer<R> consumer);

    <R extends java.lang.Object> java.util.List<R> list();

    <R> java.util.List<R> list(java.lang.Class<R> itemClass);

    <R extends java.lang.Object> R first();

    <R> R first(java.lang.Class<R> itemClass);

    spoon.reflect.visitor.chain.CtQuery failurePolicy(spoon.reflect.visitor.chain.QueryFailurePolicy policy);

    spoon.reflect.visitor.chain.CtQuery name(java.lang.String name);

    @java.lang.Override
    <I> spoon.reflect.visitor.chain.CtQuery map(spoon.reflect.visitor.chain.CtConsumableFunction<I> queryStep);

    void terminate();

    boolean isTerminated();
}

