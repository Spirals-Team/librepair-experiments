package ru.job4j.set;


public class MapSet<E> {
    private SimpleHashMap map = new SimpleHashMap<>();
    private int size = 0;

    public boolean add(E element) {
        if (!this.contains(element)) {
            map.put(element, element);
            size++;
        }
        return true;
    }

    public E get(E key) {
        return (E) map.get(key);
    }

    public boolean contains(E element) {
        return map.containsValue(element);
    }

    public void remove(E element) {
        map.remove(element);
    }
}
