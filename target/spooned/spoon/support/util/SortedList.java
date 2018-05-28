package spoon.support.util;


public class SortedList<E> extends java.util.LinkedList<E> {
    private static final long serialVersionUID = 1L;

    java.util.Comparator<? super E> comparator;

    public SortedList(java.util.Comparator<? super E> comparator) {
        super();
        this.comparator = comparator;
    }

    @java.lang.Override
    public boolean add(E o) {
        for (java.util.ListIterator<E> iterator = this.listIterator(); iterator.hasNext();) {
            E e = iterator.next();
            if ((comparator.compare(o, e)) < 0) {
                iterator.previous();
                iterator.add(o);
                return true;
            }
        }
        return super.add(o);
    }

    @java.lang.Override
    public void add(int index, E element) {
        throw new java.lang.IllegalArgumentException("cannot force a position with a sorted list that has its own ordering");
    }

    @java.lang.Override
    public boolean addAll(java.util.Collection<? extends E> c) {
        boolean ret = true;
        for (E e : c) {
            ret &= add(e);
        }
        return ret;
    }

    public java.util.Comparator<? super E> getComparator() {
        return comparator;
    }

    public void setComparator(java.util.Comparator<? super E> comparator) {
        this.comparator = comparator;
    }
}

