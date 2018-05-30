package spoon.experimental.modelobs.context;


public abstract class CollectionContext<T extends java.util.Collection<?>> extends spoon.experimental.modelobs.context.Context {
    protected final T copyOfTheCollection;

    public CollectionContext(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, T copyOfTheCollection) {
        super(element, role);
        this.copyOfTheCollection = copyOfTheCollection;
    }
}

