/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api.cache;

public interface Watcher<T> {
    void onSync();
    void onAdd(T t);
    void onUpdate(T old, T updated);
    void onDelete(T t);
}
