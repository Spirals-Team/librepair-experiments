package spoon.test.reference.testclasses;


public class Panini<K, V> {
    java.util.Iterator<java.util.Map.Entry<K, V>> entryIterator() {
        return new Itr<java.util.Map.Entry<K, V>>() {
            @java.lang.Override
            java.util.Map.Entry<K, V> output(K key, V value) {
                return null;
            }
        };
    }

    private abstract class Itr<T> implements java.util.Iterator<T> {
        @java.lang.Override
        public T next() {
            return null;
        }

        @java.lang.Override
        public boolean hasNext() {
            return false;
        }

        @java.lang.Override
        public void remove() {
        }

        abstract T output(K key, V value);
    }
}

