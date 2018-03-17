package ru.skorikov;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 19.02.18
 * @ version: java_kurs_standart
 */
public class MyNotBlockClass {

    /**
     * Хранилище моделей.
     */
    private ConcurrentHashMap<Integer, Model> map;

    /**
     * Ключ для карты - хранилища моделей.
     */
    private AtomicInteger key = new AtomicInteger(0);

    /**
     * Конструктор.
     */
    MyNotBlockClass() {
        this.map = new ConcurrentHashMap();
    }

    /**
     * Добавить модель в карту.
     *
     * @param model модель.
     */
    public void add(Model model) {
        if (!map.contains(model)) {
            map.put(key.getAndIncrement(), model);
        }
    }

    /**
     * Обновить модель.
     *
     * @param key         ключ.
     * @param updateModel обновленная модель.
     */
    public void update(Integer key, Model updateModel) {
        map.computeIfPresent(key, (Integer k, Model model) -> {
            if (map.get(key).getVersion().equals(updateModel.getVersion())) {
                Integer newVers = map.get(key).getVersion();
                newVers++;
                updateModel.setVersion(newVers);
            } else {
                try {
                    throw new OptimisticException();
                } catch (OptimisticException e) {
                    e.printStackTrace();
                }
            }
            return updateModel;
        });
    }

    /**
     * Удалить модель из карты.
     *
     * @param model модель.
     */
    public void delete(Model model) {
        map.values().removeIf(model1 -> model1.equals(model));
    }

    /**
     * Получить модель из карты по ключу.
     *
     * @param key ключ.
     * @return модель.
     */
    public Model getModel(Integer key) {
        if (map.get(key) != null) {
            Model model = new Model(map.get(key).getName(), map.get(key).getData());
            model.setVersion(map.get(key).getVersion());
            return model;
        } else {
            throw new NoSuchElementException();
        }
    }
}



