package ru.job4j.generic;


/**
 *Класс SimpleArray.
 *@author ifedorenko
 *@since 20.09.2017
 *@version 1
 *@param <T> generic
 */
public class SimpleArray<T> {
    /**
     * @param objects array for Object.
     */
    private Object[] objects;
    /**
     * @param index iindex of array
     */
    private int index = 0;

    /**
     * Constructor.
     * @param size size of array
     */
    public SimpleArray(int size) {
        this.objects = new Object[size];
    }

    /**
     * Method add.
     * @param value object
     */
    public void add(T value) {
        this.objects[index++] = value;
    }

    /**
     * Method get.
     * @param position position in array
     * @return object by generic T.
     */
    public T get(int position) {
        return (T) this.objects[position];
    }

    /**
     * Method update.
     * @param position position in array.
     * @param value object for update
     */
    public void update(int position, T value) {
        this.objects[position] = value;
    }

    /**
     * Method delete.
     * @param value object for delete
     */
    public void delete(T value) {
        for (int i = 0; i < objects.length; i++) {
            int pos = objects.length - 1 - i;
            if (objects[i].equals(value)) {
                System.arraycopy(objects, i + 1, objects, i, pos);
                index--;
                break;
            }
        }
    }

    /**
     * Array length.
     * @return length.
     */
    public int getSize() {
        return this.objects.length;
    }
}