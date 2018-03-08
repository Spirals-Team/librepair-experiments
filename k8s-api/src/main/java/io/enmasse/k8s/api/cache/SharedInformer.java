/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api.cache;

import io.enmasse.k8s.api.Watch;
import io.fabric8.kubernetes.api.model.HasMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;

public class SharedInformer<T extends HasMetadata> implements Watch {
    private static final Logger log = LoggerFactory.getLogger(SharedInformer.class.getName());
    private final Store<T> clientState;
    private final Controller<T> controller;
    private Thread thread;

    private SharedInformer(Store<T> clientState, Controller<T> controller) {
        this.clientState = clientState;
        this.controller = controller;
    }

    public void start() {
        thread = new Thread(controller);
        thread.start();
    }

    public void stop() throws InterruptedException {
        controller.shutdown();
        thread.interrupt();
        thread.join();
    }

    public Store<T> getClientState() {
        return clientState;
    }

    public static <T extends HasMetadata> SharedInformer<T> create(ListerWatcher<T> listerWatcher, Watcher<T> watcher, Duration resyncPeriod, int maxQueueSize) {
        return create(listerWatcher, watcher, Clock.systemUTC(), resyncPeriod, maxQueueSize);
    }

    public static <T extends HasMetadata> SharedInformer<T> create(ListerWatcher<T> listerWatcher, Watcher<T> watcher, Clock clock, Duration resyncPeriod, int maxQueueSize) {
        Store<T> clientState = new ObjectStore<>(obj -> obj.getMetadata().getName());
        return create(listerWatcher, clientState, watcher, clock, resyncPeriod, maxQueueSize);
    }

    public static <T extends HasMetadata> SharedInformer<T> create(ListerWatcher<T> listerWatcher, Store<T> store, Watcher<T> watcher, Duration resyncPeriod, int maxQueueSize) {
        return create(listerWatcher, store, watcher, Clock.systemUTC(), resyncPeriod, maxQueueSize);
    }

    public static <T extends HasMetadata> SharedInformer<T> create(ListerWatcher<T> listerWatcher, Store<T> clientState, Watcher<T> watcher, Clock clock, Duration resyncPeriod, int maxQueueSize) {

        WorkQueue<T> workQueue = new FifoQueue<>(maxQueueSize);

        Controller.Config<T> config = new Controller.Config<>();

        config.setClock(clock);
        config.setListerWatcher(listerWatcher);
        config.setResyncInterval(resyncPeriod);
        config.setWorkQueue(workQueue);
        config.setProcessor(event -> {
            switch (event.getEventType()) {
                case SYNC:
                    log.info("onSync");
                    watcher.onSync();
                    break;
                case ADDED:
                case UPDATED:
                    T old = clientState.get(event.getObj());
                    if (old != null) {
                        clientState.update(event.getObj());
                        log.info("onUpdate");
                        watcher.onUpdate(old, event.getObj());
                    } else {
                        clientState.add(event.getObj());
                        log.info("onAdd");
                        watcher.onAdd(event.getObj());
                    }
                    break;
                case DELETED:
                    clientState.delete(event.getObj());
                    log.info("onDelete");
                    watcher.onDelete(event.getObj());
                    break;
            }
        });

        Controller<T> controller = new Controller<>(config);
        return new SharedInformer<>(clientState, controller);
    }

    @Override
    public void close() throws Exception {
        stop();
    }
}
