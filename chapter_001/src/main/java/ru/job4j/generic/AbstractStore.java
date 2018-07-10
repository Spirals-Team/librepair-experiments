package ru.job4j.generic;

/**
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {

    private SimpleArray<T> models;

    public AbstractStore(int size) {
        this.models = new SimpleArray<>(size);
    }

    public void add(T model) {
        models.add(model);
    }

    public boolean replace(String id, T model) {
        int idx = findIdx(id);
        if (idx == -1) {
            return false;
        }
        models.update(idx, model);
        return true;
    }

    public boolean delete(String id) {
        int idx = findIdx(id);
        if (idx == -1) {
            return false;
        }
        models.delete(idx);
        return true;
    }

    public T findById(String id) {
        int idx = findIdx(id);
        if (idx == -1) {
            throw new IllegalArgumentException();
        }
        return models.get(idx);
    }

    private int findIdx(String id) {
        for (int idx = 0; idx < models.size(); idx++) {
            T model = models.get(idx);
            if (model.getId().equals(id)) {
                return idx;
            }
        }
        return -1;
    }
}
