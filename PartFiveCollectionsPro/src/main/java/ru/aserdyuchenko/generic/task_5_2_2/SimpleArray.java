package ru.aserdyuchenko.generic.task_5_2_2;

/**
 * @author anton.
 */

/**
 * Class.
 * @param <T> - just tupe.
 */
public class SimpleArray<T> {

    /**
     * @param objects - Array with objects.
     */
    private Object[] objects;

    /**
     * @param index - index for array.
     */
    private int index = 0;

    /**
     * Constructor.
     * @param size - size for array.
     */
    public SimpleArray(int size) {
        this.objects = new Object[size];
    }

    /**
     * Add new value.
     * @param value - value for add.
     */
    public void add(Base value) {
        this.objects[index++] = value;
    }

    /**
     * Update.
     * @param position - position for update.
     * @param value - new value.
     */
    public void update(int position, Base value) {
        this.objects[position] = value;
    }

    /**
     * Delete.
     * @param position - position value for remove.
     */
    public void delete(int position) {
        Object[] objectsNew = objects;
        int positionNew = position + 1;
        System.arraycopy(objectsNew, positionNew, objects, position, objects.length - (position + 1));
    }

    /**
     * Get.
     * @param position - position.
     * @return this object.
     */
    public T get(int position) {
        return (T) this.objects[position];
    }
}
