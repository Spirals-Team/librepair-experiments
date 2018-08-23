package ru.job4j.set;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Class SpeedSet.
 * @param <E> generic
 */
public class SpeedSet<E extends Comparable<E>> extends  AbstractArray implements  SimpleSet<E>, Comparator<E> {
    /**
     * Base constructor.
     */
    public SpeedSet() {
        super();
    }

    /**
     * Constructor.
     *
     * @param initialCapacity start size.
     */
    public SpeedSet(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Method add.
     * @param e element for adding.
     */
    public void add(E e) {
        if (index >= objects.length) {
            objects = Arrays.copyOf(objects, objects.length * 2);
        }
        if (index > 0) {
            int left = 0;
            int right = index - 1;
            while (left < right) {
                int mid = (left + right) / 2;
                int eq = compare(e, (E) objects[mid]);
                if (eq == 0) {
                    break;
                } else if (eq > 0) {
                    left = mid + 1;
                } else if (eq < 0) {
                    right = mid - 1;
                }
            }
            if (left > right) {
                System.arraycopy(objects, 0, objects, 1, index);
                objects[0] = e;
                index++;
            } else if (left == right) {
                int eq = compare(e, (E) objects[left]);
                if (eq < 0) {
                    int count = index - left;
                    System.arraycopy(objects, left, objects, left + 1, count);
                    objects[left] = e;
                    index++;
                } else if (eq > 0 && left == index - 1) {
                    objects[index++] = e;
                } else if (eq > 0) {
                    int count = index - left - 1;
                    System.arraycopy(objects, left + 1, objects, left + 2, count);
                    objects[left + 1] = e;
                    index++;
                }
            }
        } else {
            objects[index++] = e;
        }
    }

    @Override
    public int compare(E o1, E o2) {
        if (o1.equals(o2)) {
            return 0;
        } else {
            return o1.compareTo(o2);
        }
    }
}

