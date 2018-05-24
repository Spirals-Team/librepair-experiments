package spoon.reflect.visitor.filter;


public class ReferenceTypeFilter<T extends spoon.reflect.reference.CtReference> extends spoon.reflect.visitor.filter.TypeFilter<T> {
    public ReferenceTypeFilter(java.lang.Class<? super T> type) {
        super(type);
    }
}

