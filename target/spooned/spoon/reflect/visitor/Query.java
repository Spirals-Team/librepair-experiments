package spoon.reflect.visitor;


public abstract class Query {
    private Query() {
    }

    public static <E extends spoon.reflect.declaration.CtElement> java.util.List<E> getElements(spoon.reflect.factory.Factory factory, spoon.reflect.visitor.Filter<E> filter) {
        return spoon.reflect.visitor.Query.getElements(factory.Package().getRootPackage(), filter);
    }

    public static <E extends spoon.reflect.declaration.CtElement> java.util.List<E> getElements(spoon.reflect.declaration.CtElement rootElement, spoon.reflect.visitor.Filter<E> filter) {
        return rootElement.filterChildren(filter).list();
    }

    public static <T extends spoon.reflect.reference.CtReference> java.util.List<T> getReferences(spoon.reflect.declaration.CtElement rootElement, spoon.reflect.visitor.Filter<T> filter) {
        return rootElement.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtReference.class)).filterChildren(filter).list();
    }

    public static <R extends spoon.reflect.reference.CtReference> java.util.List<R> getReferences(spoon.reflect.factory.Factory factory, spoon.reflect.visitor.Filter<R> filter) {
        return spoon.reflect.visitor.Query.getReferences(factory.Package().getRootPackage(), filter);
    }
}

