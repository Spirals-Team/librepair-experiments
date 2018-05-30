package spoon.experimental.modelobs.context;


public class ListContext extends spoon.experimental.modelobs.context.CollectionContext<java.util.List<?>> {
    private final int position;

    public ListContext(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, java.util.List<?> original) {
        this(element, role, original, (-1));
    }

    public ListContext(spoon.reflect.declaration.CtElement element, spoon.reflect.path.CtRole role, java.util.List<?> original, int position) {
        super(element, role, original);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}

