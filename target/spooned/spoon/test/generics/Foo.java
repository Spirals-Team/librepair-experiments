package spoon.test.generics;


public class Foo<K, V> {
    protected spoon.test.generics.Bar<K, V> meth1() {
        return new spoon.test.generics.Bar<K, V>() {
            public V transform(final K input) {
                if (input instanceof java.lang.String) {
                    return ((V) ("NULL"));
                }
                return ((V) ("NULL_OBJECT"));
            }
        };
    }
}

