package spoon.legacy;


@java.lang.Deprecated
public class NameFilter<T extends spoon.reflect.declaration.CtNamedElement> implements spoon.reflect.visitor.Filter<T> {
    private final java.lang.String name;

    public NameFilter(java.lang.String name) {
        if (name == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.name = name;
    }

    public boolean matches(T element) {
        try {
            return name.equals(element.getSimpleName());
        } catch (java.lang.UnsupportedOperationException e) {
            return false;
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    public java.lang.Class<T> getType() {
        return ((java.lang.Class<T>) (spoon.reflect.declaration.CtNamedElement.class));
    }
}

