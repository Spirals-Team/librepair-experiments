package spoon.test.generics;


class Tree<V extends java.io.Serializable & java.lang.Comparable<V>> {
    V node() {
        return null;
    }

    <T> T node2() {
        return null;
    }

    @java.lang.SuppressWarnings("unused")
    <T extends spoon.test.generics.Tree<V> & java.lang.Comparable<T>> T node3() {
        java.util.List<V> l = new java.util.ArrayList<V>();
        if (l == null);
        return null;
    }

    <T extends spoon.test.generics.Tree<V> & java.lang.Comparable<T>> T node4() {
        return null;
    }

    <T, R extends java.lang.Comparable<? super T>> T node5() {
        this.<java.lang.Class<? extends java.lang.Throwable>>foo();
        return null;
    }

    <T> void foo() {
    }
}

