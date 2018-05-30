package spoon.reflect.visitor.filter;


public abstract class AbstractReferenceFilter<T extends spoon.reflect.reference.CtReference> extends spoon.reflect.visitor.filter.AbstractFilter<T> implements spoon.reflect.visitor.Filter<T> {
    @java.lang.SuppressWarnings("unchecked")
    public AbstractReferenceFilter(java.lang.Class<? super T> type) {
        super(type);
    }

    public AbstractReferenceFilter() {
        super();
    }
}

