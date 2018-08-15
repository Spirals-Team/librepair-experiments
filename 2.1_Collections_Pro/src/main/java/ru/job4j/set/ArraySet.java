package ru.job4j.set;

import java.util.Objects;

/**
 * Class ArraySet.
 * @param <E> generic
 */
public class ArraySet<E> extends AbstractArray implements SimpleSet<E> {
    /**
     * Base constructor.
     */
    public ArraySet() {
        super();
    }

    /**
     * Constructor.
     * @param initialCapacity start size.
     */
    public ArraySet(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Method add.
     * @param e element for adding.
     */
    @Override
    public void add(E e) {
        if (checkDuplicate(e)) {
            super.put(e);
        }
    }

    /**
     * Method checkDuplicate.
     * @param e element for checking.
     * @return result
     */
    public boolean checkDuplicate(E e) {
        boolean result = true;
        for (Object ob : objects) {
            if (Objects.equals(e, ob)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Getter index.
     * @return index
     */
    public int getIndex() {
        return index;
    }
}
