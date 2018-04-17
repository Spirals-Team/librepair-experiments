package ru.job4j.list;

import java.util.Iterator;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class DynamicArray<E> implements ISimpleContainer<E> {

    private Object[] container = new Object[1];
    private int modCount = 0;

    @Override
    public void add(E e) {
        if (!this.checkFreeCell()) {
            container = createBiggerArray();
        }
        container[container.length - 1] = e;
        modCount++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {

        return (E) container[index];
    }

    @Override
    public Iterator<E> iterator() {
        return new DynamicArrayIterator<>(this);
    }

    public int getModCount() {
        return modCount;
    }

    public Object[] getContainer() {
        return container;
    }

    private boolean checkFreeCell() {
        return container[container.length - 1] == null;
    }

    private Object[] createBiggerArray() {
        int lengthSrc = container.length;
        Object[] newContainer = new Object[lengthSrc + 1];
        System.arraycopy(container, 0, newContainer, 0, lengthSrc);
        return newContainer;
    }
}
