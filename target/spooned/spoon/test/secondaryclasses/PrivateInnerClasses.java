package spoon.test.secondaryclasses;


public class PrivateInnerClasses<P> {
    private class DataClassComparator<T> implements java.util.Comparator<java.lang.Class<T>> {
        public DataClassComparator() {
        }

        @java.lang.Override
        public int compare(java.lang.Class<T> c1, java.lang.Class<T> c2) {
            return c1.getName().compareTo(c2.getName());
        }
    }

    public PrivateInnerClasses() {
        spoon.test.secondaryclasses.PrivateInnerClasses<P>.DataClassComparator<java.lang.Object> c1 = new DataClassComparator<>();
        spoon.test.secondaryclasses.PrivateInnerClasses<P>.DataClassComparator<java.lang.Class<P>> c2 = new DataClassComparator<java.lang.Class<P>>();
        java.lang.System.out.println((((" - " + c1) + ",") + c2));
    }
}

