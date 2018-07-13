package ru.job4j.threads;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Класс реализует неблокирующийся кеш
 */
public class NonBlocking {

    // хранилище
    private ConcurrentHashMap<Integer, Model> cache = new ConcurrentHashMap<>();

    /**
     * Внутренний класс для модели
     */
    class Model {
        private final Integer id;
        private String name;
        private int version;

        public Model(Integer id, String name) {
            this.name = name;
            this.id = id;
            this.version = 1;
        }

        // метод для тестов
        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Model model = (Model) o;

            return id.equals(model.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }

    /**
     * Метод ищет модель по id. Только для тестов.
     * @param id айди
     * @return модель
     */
    public Model findById(Integer id) {
        return cache.get(id);
    }

    /**
     * Метод реализует добавление модели в кеш
     * Если модель с таким айди уже существует то дабавления не происходит
     * @param mod модель
     */
    public void add(final Model mod) {
        cache.putIfAbsent(mod.id, mod);
    }

    /**
     * Метод реализует удаление модели их кеша
     * Если модели с таким айди не существует то удаления не происходит
     * @param mod модель
     */
    public void delete(final Model mod) {
        if (cache.containsKey(mod.id)) {
            cache.remove(mod.id);
        }
    }

    /**
     * Метод изменяет имя модели в кеше.
     * Если версии моделей не совпадают то вылетает ошибка
     * @param mod модель
     */
    public void update(final Model mod) {
        this.cache.computeIfPresent(mod.id, new BiFunction<Integer, Model, Model>() {
            @Override
            public Model apply(Integer id, Model m) {
                if (m.version != mod.version) {
                    throw new OptimisticException("Error");
                }
                m.name = mod.name;
                m.version++;
                return m;
            }
        });
    }

}
