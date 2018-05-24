package spoon.support.util;


public class QualifiedNameBasedSortedSet<E extends spoon.reflect.declaration.CtElement> extends java.util.TreeSet<E> {
    private static final long serialVersionUID = 1L;

    public QualifiedNameBasedSortedSet(java.util.Collection<E> elements) {
        this();
        addAll(elements);
    }

    public QualifiedNameBasedSortedSet() {
        super(new spoon.support.comparator.QualifiedNameComparator());
    }
}

