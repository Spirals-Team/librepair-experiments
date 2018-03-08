/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api.cache;

import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;

import java.util.List;

public interface ListerWatcher<T> {
    List<T> list();
    Watch watch(Watcher<T> watcher);
}
