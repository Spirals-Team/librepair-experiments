package ru.job4j.generic;

public abstract class AbstractStore<T extends Base> implements Store<T> {
    private SimpleArray<T> store;

    public AbstractStore(int size) {
        this.store = new SimpleArray<>(size);
    }

    public T getElement(String id) {
        int position = 0;
        for (T value : this.store) {
            if (value.getId().equals(id)) {
                return this.store.get(position);
            }
            position++;
        }
        return null;
    }

    @Override
    public T add(T model) {
        this.store.add(model);
        return model;
    }
    
    @Override
    public T update(T model) {
        int position = 0;
        for (T value : this.store) {
            if (value.getId().equals(model.getId())) {
                this.store.update(model, position);
                return model;
            }
            position++;
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        int position = 0;
        for (T value : this.store) {
            if (value.getId().equals(id)) {
                this.store.delete(position);
                return true;
            }
            position++;
        }
        return false;
    }
}
