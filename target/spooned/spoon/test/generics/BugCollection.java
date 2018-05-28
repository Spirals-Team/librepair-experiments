package spoon.test.generics;


public class BugCollection<K, V> {
    public static final spoon.test.generics.ACLass<?> INSTANCE = new spoon.test.generics.ACLass();

    @java.lang.SuppressWarnings("rawtypes")
    public static final spoon.test.generics.ACLass<?> INSTANCE2 = new spoon.test.generics.ACLass();

    java.util.Map.Entry x;

    java.util.Map.Entry<?, ?> y;

    java.util.Map.Entry<java.lang.String, java.lang.Integer> z;

    void foo() {
        x = null;
        java.util.Map.Entry lx;
        java.util.Map.Entry<?, ?> ly;
        java.util.Map.Entry<java.lang.String, java.lang.Integer> lz;
        java.util.Iterator<java.util.Map.Entry<?, ?>> it;
    }

    class Foo implements java.util.Set<java.util.Map.Entry<K, V>> {
        @java.lang.Override
        public int size() {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public boolean isEmpty() {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public boolean contains(java.lang.Object o) {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public java.util.Iterator<java.util.Map.Entry<K, V>> iterator() {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public java.lang.Object[] toArray() {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public <T> T[] toArray(T[] a) {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public boolean add(java.util.Map.Entry<K, V> e) {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public boolean remove(java.lang.Object o) {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public boolean containsAll(java.util.Collection<?> c) {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public boolean addAll(java.util.Collection<? extends java.util.Map.Entry<K, V>> c) {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public boolean retainAll(java.util.Collection<?> c) {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public boolean removeAll(java.util.Collection<?> c) {
            throw new java.lang.UnsupportedOperationException();
        }

        @java.lang.Override
        public void clear() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}

