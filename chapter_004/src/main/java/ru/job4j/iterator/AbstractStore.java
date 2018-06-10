package ru.job4j.iterator;

public class AbstractStore<T extends Base> implements Store<T> {

    SimpleArray array = new SimpleArray();

    @Override
    public void add(T model) {
        array.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        boolean result = false;
        int index = Integer.parseInt(id);
        if (array.get(index) != null) {
            result = true;
        }
        array.set(index, model);
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        int index = Integer.parseInt(id);
        if (array.delete(index) != null) {
            result = true;
        }
        return result;
    }

    @Override
    public T findById(String id) {
        return (T) array.get(Integer.parseInt(id));
    }
}
