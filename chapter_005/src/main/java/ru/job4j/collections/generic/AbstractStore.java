package ru.job4j.collections.generic;

/**
 * Абстракный класс для хранилищь.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 06.10.17;
 * @version $Id$
 * @since 0.1
 * @param <E> тип хранимых данных.
 */
public class AbstractStore<E extends Base> implements Store {
    /**
     * Хранилище данных.
     */
    private SimpleArray<E> simpleArray;
    /**
     * размер хранилища.
     */
    private int size;
    /**
     * Конструктор.
     * @param size размер хранилища.
     */
    public AbstractStore(int size) {
        this.simpleArray = new SimpleArray<E>(size);
        this.size = size;
    }

    /**
     * Метод добовления новых элементов в хранилище.
     * @param model элемент.
     * @return возврат добавленного элемента.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Base add(Base model) {
        simpleArray.add((E) model);
        return model;
    }

    /**
     * Обновление элемента.
     * @param model элемент.
     * @return возврат обновленного элемента.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Base update(Base model) {
        simpleArray.update((E) model);
        return model;
    }

    /**
     * Удаление элемента.
     * @param id id элемента.
     * @return удачно или нет.
     */
    @Override
    public boolean delete(String id) {
        Base base = null;
        for (int i = 0; i < size; i++) {
            base = simpleArray.getValue(i);
            if (id.equals(base.getId())) {
                simpleArray.delete(i);
                break;
            }
        }
        return base != null;
    }

    /**
     * возвращает элемент по номеру.
     * @param index номер.
     * @return элемент.
     */
    public Base getByIndex(int index) {
        return  simpleArray.getValue(index);
    }
}
