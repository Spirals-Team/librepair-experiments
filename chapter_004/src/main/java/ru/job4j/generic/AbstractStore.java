package ru.job4j.generic;

import java.util.Iterator;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {
    private SimpleArray<T> array;

    public AbstractStore(int maxSize) {
        this.array = new SimpleArray<>(maxSize);
    }

    public SimpleArray<T> getArray() {
        return array;
    }

    public int findIndex(String id) {
        Iterator<T> it = array.iterator();
        T current = null;
        int index = -1;
        while (it.hasNext()) {
            index++;
            current = it.next();
            if (current.getId().equals(id)) {
                break;
            }
        }
        if (current == null || !current.getId().equals(id)) {
            throw new IndexOutOfBoundsException(String.format("Элемент с id: %s не найден", id));
        }
        return index;
    }

    @Override
    public void add(T model) {
        this.getArray().add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        this.getArray().set(this.findIndex(id), model);
        return true;
    }

    @Override
    public boolean delete(String id) {
        this.getArray().delete(this.findIndex(id));
        return true;
    }

    @Override
    public T findById(String id) {
        return this.getArray().get(this.findIndex(id));
    }
}
