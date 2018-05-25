package spoon.reflect.visitor.filter;


public class NamedElementFilter<T extends spoon.reflect.declaration.CtNamedElement> implements spoon.reflect.visitor.Filter<T> {
    private final java.lang.String name;

    private java.lang.Class<T> acceptedClass;

    public NamedElementFilter(java.lang.Class<T> acceptedClass, java.lang.String name) {
        if ((name == null) || (acceptedClass == null)) {
            throw new java.lang.IllegalArgumentException();
        }
        this.name = name;
        this.acceptedClass = acceptedClass;
    }

    public boolean matches(T element) {
        try {
            return (acceptedClass.isAssignableFrom(element.getClass())) && (name.equals(element.getSimpleName()));
        } catch (java.lang.UnsupportedOperationException e) {
            return false;
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    public java.lang.Class<T> getType() {
        return acceptedClass;
    }
}

