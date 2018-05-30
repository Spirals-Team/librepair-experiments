package spoon.test.generics;


public class ComparableComparatorBug<E extends java.lang.Comparable<? super E>> implements java.io.Serializable , java.util.Comparator<E> {
    private static final long serialVersionUID = -291439688585137865L;

    @java.lang.SuppressWarnings("rawtypes")
    public static final spoon.test.generics.ComparableComparatorBug INSTANCE = new spoon.test.generics.ComparableComparatorBug();

    @java.lang.SuppressWarnings("unchecked")
    public static <E extends java.lang.Comparable<? super E>> spoon.test.generics.ComparableComparatorBug<E> comparableComparator() {
        return spoon.test.generics.ComparableComparatorBug.INSTANCE;
    }

    public ComparableComparatorBug() {
        super();
    }

    public int compare(final E obj1, final E obj2) {
        return obj1.compareTo(obj2);
    }

    @java.lang.Override
    public int hashCode() {
        return "ComparableComparator".hashCode();
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object object) {
        return ((this) == object) || ((null != object) && (object.getClass().equals(this.getClass())));
    }
}

