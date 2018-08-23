package ru.job4j.set;


import net.jcip.annotations.ThreadSafe;

/**
 * Class ArrayContainer.
 * @param <E> generic
 */
@ThreadSafe
public class ArrayContainer<E> extends AbstractArray implements SimpleContainer<E> {
    /**
     * @param obj for sync
     */
    private static final Object OBJ = new Object();

    /**
     * Base constructor.
     */
    public ArrayContainer() {
        super();
    }

    /**
     * Constructor.
     * @param initialCapacity start size
     */
    public ArrayContainer(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public void add(E e) {
        synchronized (OBJ) {
            super.put(e);
        }
    }

    /**
     * Method get.
     * @param index position for return.
     * @return object.
     */
    @Override
    public E get(int index) {
        synchronized (OBJ) {
            E result = null;
            if (index >= super.objects.length) {
                throw new IndexOutOfBoundsException();
            } else {
                result = (E) objects[index];
            }
            return result;
        }
    }
}
