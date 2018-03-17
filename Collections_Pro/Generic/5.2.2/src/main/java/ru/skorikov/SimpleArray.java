package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <T> Параметр.
 * @ author: Alex_Skorikov.
 * @ date: 09.10.17
 * @ version: java_kurs_standart
 * 5.2.2. Реализовать Store<T extends Base>.
 * Параметризованный класс.
 */
public class SimpleArray<T>  {

    /**
     * Поле массив объектов.
     */
    private Object[] objects;

    /**
     * Поле - счетчик.
     */
    private int index = 0;

    /**
     * Конструктор.
     *
     * @param size размер массива объектов.
     */
    public SimpleArray(int size) {
        this.objects = new Object[size];
    }

    /**
     * Получить массив объектов.
     * @return массив объектов.
     */
    public Object[] getObjects() {
        return objects;
    }

    /**
     * Добавить элемент в массив.
     *
     * @param value элемент.
     */
    public void add(T value) {
        if (index <= objects.length) {
            this.objects[index++] = value;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Получить элемент массива.
     *
     * @param position позиция элемента.
     * @return элемент.
     */
    public T get(int position) {
        if (position < objects.length) {
            return (T) this.objects[position];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Обновить элемент в массиве.
     *
     * @param index индекс в массиве.
     * @param value элемент.
     */
    public void update(int index, T value) {
        if (index <= objects.length) {
            objects[index] = value;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Удалить элемент.
     *
     * @param value элемент.
     */
    public void delete(T value) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null && objects[i].equals(value)) {
                objects[i] = null;
                System.arraycopy(this.objects, i + 1,
                        this.objects, i, this.objects.length - 1 - i);
                objects[objects.length - 1] = null;
                index--;
            }
        }
    }
}
