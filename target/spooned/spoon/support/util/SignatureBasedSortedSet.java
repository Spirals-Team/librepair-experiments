package spoon.support.util;


public class SignatureBasedSortedSet<E extends spoon.reflect.declaration.CtExecutable<?>> extends java.util.TreeSet<E> {
    private static final long serialVersionUID = 1L;

    public SignatureBasedSortedSet(java.util.Collection<E> elements) {
        this();
        addAll(elements);
    }

    public SignatureBasedSortedSet() {
        super(new spoon.support.comparator.SignatureComparator());
    }
}

