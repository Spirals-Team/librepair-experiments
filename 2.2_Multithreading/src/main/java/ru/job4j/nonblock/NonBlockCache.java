package ru.job4j.nonblock;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Class NonBlockCache
 */
public class NonBlockCache {
    /**
     * @param cache map for saving models.
     */
    private ConcurrentHashMap<Integer, Model> cache = new ConcurrentHashMap<>();

    /**
     * Method add.
     * @param key key for adding in cache
     * @param name name for model
     */
    public void add(Integer key, String name) {
        cache.put(key, new Model(name));
    };

    /**
     * Method update.
     * @param key key for cache
     * @param name name for model
     */
    public void update(Integer key, String name) {
        Model temp = new Model(name);
        temp.version = cache.get(key).version;
        cache.computeIfPresent(key, (k, model) -> {
            if (cache.get(k).version == temp.version) {
                temp.version++;
                return temp;
            } else {
                throw new OptimisticException("Model already update");
            }
        });
    }

    /**
     * Method delete.
     * @param key key for cahce
     */
    public void delete(Integer key) {
        cache.remove(key);
    }

    /**
     * Inner class Model.
     */
    class Model {
        /**
         * @param name name by model
         */
        private String name;
        /**
         * @version current version by model
         */
        private int version = 0;

        /**
         * Constructor.
         * @param name name for model
         */
        public Model(String name) {
            this.name = name;
        }
    }

    /**
     * Method size.
     * @return size
     */
    public int size() {
        return cache.size();
    }
}
