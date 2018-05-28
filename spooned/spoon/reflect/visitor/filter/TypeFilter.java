package spoon.reflect.visitor.filter;


public class TypeFilter<T extends spoon.reflect.declaration.CtElement> extends spoon.reflect.visitor.filter.AbstractFilter<T> {
    public TypeFilter(java.lang.Class<? super T> type) {
        super(type);
    }
}

