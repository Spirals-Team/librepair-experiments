package spoon.reflect.path.impl;


public class CtTypedNameElement<P extends spoon.reflect.declaration.CtElement, T extends spoon.reflect.declaration.CtElement> extends spoon.reflect.path.impl.AbstractPathElement<P, T> {
    public static final java.lang.String STRING = "/";

    private final java.lang.Class<T> type;

    public CtTypedNameElement(java.lang.Class<T> type) {
        this.type = type;
    }

    public java.lang.Class<T> getType() {
        return type;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((spoon.reflect.path.impl.CtTypedNameElement.STRING) + (type.getSimpleName())) + (getParamString());
    }

    @java.lang.Override
    public java.util.Collection<T> getElements(java.util.Collection<P> roots) {
        java.util.Collection<T> elements = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtElement root : roots) {
            for (spoon.reflect.declaration.CtElement child : getChilds(root)) {
                if (match(child)) {
                    elements.add(((T) (child)));
                }
            }
        }
        return elements;
    }

    private boolean match(spoon.reflect.declaration.CtElement element) {
        return (element != null) && (type.isAssignableFrom(element.getClass()));
    }
}

