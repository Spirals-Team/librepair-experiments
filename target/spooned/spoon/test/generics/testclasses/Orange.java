package spoon.test.generics.testclasses;


public class Orange<K, L> {
    class A<O extends K, M extends O> {
        java.util.List<java.util.List<M>> list2m;

        void method(java.util.List<? extends M> param) {
        }
    }

    class B<N extends K, P extends N> extends spoon.test.generics.testclasses.Orange<K, L>.A<N, P> {}
}

