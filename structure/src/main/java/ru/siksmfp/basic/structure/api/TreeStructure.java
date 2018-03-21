package ru.siksmfp.basic.structure.api;

/**
 * @author Artem Karnov @date 2/20/2018.
 * @email artem.karnov@t-systems.com
 */
public interface TreeStructure<K extends Comparable<K>, V> {
    void add(K key, V value);

    void strictAdd(K key, V value);

    V get(K key);

    boolean contains(K key);

    boolean containsValue(V value);

    void remove(K key);

    void removeValue(V value);

    int height();

    int size();
}
