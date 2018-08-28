package ru.job4j.generic.store;

import ru.job4j.generic.SimpleArray;
import ru.job4j.generic.base.Base;

public abstract class AbstractStore<T extends Base> implements Store<T> {

    private SimpleArray simpleArray;

    public AbstractStore(int size) {
        this.simpleArray = new SimpleArray(size);
    }

    @Override
    public void add(T model) {
        this.simpleArray.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        if (this.simpleArray.get(Integer.valueOf(id)) != null) {
            this.simpleArray.update(Integer.valueOf(id), model);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        if (this.simpleArray.get(Integer.valueOf(id)) != null) {
            this.simpleArray.delete(Integer.valueOf(id));
            return true;
        }
        return false;
    }

    @Override
    public T findById(String id) {
        if (this.simpleArray.get(Integer.valueOf(id)) != null) {
            return (T) this.simpleArray.get(Integer.valueOf(id));
        }
        return null;
    }
}
