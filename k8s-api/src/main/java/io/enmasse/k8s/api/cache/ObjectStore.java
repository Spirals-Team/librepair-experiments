/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectStore<T> implements Store<T> {
    private final Map<String, T> objects = new HashMap<>();
    private final KeyExtractor<T> keyExtractor;

    public ObjectStore(KeyExtractor<T> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public synchronized void add(T t) {
        objects.putIfAbsent(keyExtractor.getKey(t), t);
    }

    @Override
    public synchronized void update(T t) {
        String key = keyExtractor.getKey(t);
        if (objects.get(key) == null) {
            objects.put(key, t);
        } else {
            objects.replace(key, objects.get(key), t);
        }
    }

    @Override
    public synchronized void delete(T t) {
        objects.remove(keyExtractor.getKey(t));
    }

    @Override
    public synchronized T get(T t) {
        return objects.get(keyExtractor.getKey(t));
    }

    @Override
    public synchronized List<T> list() {
        return new ArrayList<>(objects.values());
    }

    @Override
    public synchronized List<String> listKeys() {
        return new ArrayList<>(objects.keySet());
    }

    @Override
    public synchronized void replace(List<T> list) {
        objects.clear();
        for (T t : list) {
            objects.put(keyExtractor.getKey(t), t);
        }
    }
}
