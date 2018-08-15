package ru.job4j.generic;

/**
 * Class AbstractStore.
 *@author ifedorenko
 *@since 25.09.2017
 *@version 1
 * @param <T> generic
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {
    /**
     * @param array array
     */
    private SimpleArray<T> array;

    /**
     * Constructor.
     * @param array array
     */
    public AbstractStore(SimpleArray array) {
        this.array = array;
    }

    /**
     * Method add.
     * @param t value adding
     */
    @Override
    public void add(T t) {
        array.add(t);
    }

    /**
     * Method update.
     * @param t value for updating.
     */
    @Override
    public void update(T t) {
        for (int i = 0; i < array.getSize(); i++) {
            if (t.getId().equals(array.get(i).getId())) {
                array.update(i, t);
                break;
            }
        }
    }

    /**
     * Getter.
     * @return array
     */
    public SimpleArray<T> getArray() {
        return array;
    }

    /**
     * Method delete.
     * @param t value for deleting
     */
    @Override
    public void delete(T t) {
        array.delete(t);
    }
}
