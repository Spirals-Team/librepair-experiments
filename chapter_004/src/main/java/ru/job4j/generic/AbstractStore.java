package ru.job4j.generic;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {
    SimpleArray<T> list = new SimpleArray<T>();

    @Override
    public void add(T model) {
        list.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                list.set(i, model);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                list.delete(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public T findById(String id) {
        for (T element: list) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        return null;
    }
}
