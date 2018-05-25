package spoon.reflect.factory;


public class QueryFactory extends spoon.reflect.factory.SubFactory {
    public QueryFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public spoon.reflect.visitor.chain.CtQuery createQuery() {
        return new spoon.reflect.visitor.chain.CtQueryImpl();
    }

    public spoon.reflect.visitor.chain.CtQuery createQuery(java.lang.Object input) {
        return new spoon.reflect.visitor.chain.CtQueryImpl(input);
    }

    public spoon.reflect.visitor.chain.CtQuery createQuery(java.lang.Iterable<?> inputs) {
        return new spoon.reflect.visitor.chain.CtQueryImpl().addInput(inputs);
    }

    public spoon.reflect.visitor.chain.CtQuery createQuery(java.lang.Object... input) {
        return new spoon.reflect.visitor.chain.CtQueryImpl(input);
    }
}

