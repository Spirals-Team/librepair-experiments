package ru.job4j.generic;

/**
 * @author Alexander Visotskiy
 * @version $Id$
 * @since 0.1
 */

public abstract class AbstractStore<T extends Base> implements Store {

    private SimpleArray<T> t;

    public AbstractStore(SimpleArray<T> t) {
        this.t = t;
    }

    @Override
    public void add(Base model) {
        t.add((T) model);
    }

    @Override
    public void replace(String id, Base model) {
        t.set(getIndex(id), (T) model);
    }

    @Override
    public void delete(String id) {
        t.delete(getIndex(id));
    }

    @Override
    public T findById(String id) {
        return t.get(getIndex(id));
    }

    public int getIndex(String id) {
        int result = -1;
        for (int i = 0; i < t.getLength(); i++) {
            if (t.get(i).getId().equals(id)) {
                result = i;
                break;
            }
        }
        return result;
    }
}
