package ru.job4j.generic;

public abstract class AbstractStore<T extends Base> implements Store<T> {
    private SimpleArray<T> array = new SimpleArray<>(10);

    @Override
    public void add(T model) {
        array.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        boolean result = false;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getId().equals(id)) {
                array.set(i, model);
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getId().equals(id)) {
                array.delete(i);
                result = true;
            }
        }
        return result;
    }

    @Override
    public T findById(String id) {
        T result = null;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getId().equals(id)) {
                result = array.get(i);
            }
        }
        return result;
    }

    public T get(int index) {
        return array.get(index);
    }
}
