package ru.job4j.generic;

/**
 * Абстрактный класс реализующий функционал хранилища для классов, потомков Base
 * @param <T> параметр класса
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {

    private SimpleArray<T> store = new SimpleArray<>(5);

    /**
     * Метод добавляет объект в хранилище
     * @param model объект
     */
    @Override
    public void add(T model) {
        store.add(model);
    }

    /**
     * Метод заменяет объект с требуемым id другим объектом
     * @param id айди требуемого объекта
     * @param model новый объект
     * @return успешность операции
     */
    @Override
    public boolean replace(String id, T model) {
        boolean result = false;
        if (findIndexById(id) > -1) {
            store.set(findIndexById(id), model);
            result = true;
        }
        return result;
    }

    /**
     * Метод удаляет объект с требуемым id
     * @param id айди требуемого объекта
     * @return успешность операции
     */
    @Override
    public boolean delete(String id) {
        boolean result = false;
        if (findIndexById(id) > -1) {
            store.delete(findIndexById(id));
            result = true;
        }
        return result;
    }

    @Override
    /**
     * Метод ищет объект с требуемым id
     * @param id айди требуемого объекта
     * @return найденный объект
     */
    public T findById(String id) {
        T result = null;
        for (T us: store) {
            if (us.getId().equals(id)) {
                result = us;
                break;
            }
        }
        return result;
    }

    /**
     * Метод ищит индекс объекта в хранилище по id
     * @param id айди требуемого объекта
     * @return индекс в хранилище
     */
    private int findIndexById(String id) {
        int result = -1;
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getId().equals(id)) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Метод ищит объект в хранилище по индексу
     * @param index индекс требуемого объекта
     * @return индекс в хранилище
     */
    public T findByIndex(int index) {
        return store.get(index);
    }
}
