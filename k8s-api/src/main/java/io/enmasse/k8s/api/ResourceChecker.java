/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import io.enmasse.k8s.api.cache.Store;
import io.enmasse.k8s.api.cache.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ResourceChecker<T> implements Watcher<T>, Runnable {
    private static final Logger log = LoggerFactory.getLogger(ResourceChecker.class.getName());
    private final CheckTask checkerTask;
    private final Duration recheckInterval;
    private final Object monitor = new Object();
    private volatile boolean synced = false;
    private volatile boolean running = false;

    private Thread thread;

    private ResourceChecker(CheckTask checkerTask, Duration recheckInterval) {
        this.checkerTask = checkerTask;
        this.recheckInterval = recheckInterval;
    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        while (running) {
            doWork();
        }
    }

    void doWork() {
        synchronized (monitor) {
            try {
                if (synced) {
                    log.info("Calling checker task");
                    checkerTask.check();
                }
                monitor.wait(recheckInterval.toMillis());
                log.info("Woke up from monitor");
            } catch (InterruptedException e) {
                Thread.interrupted();
            } catch (Exception e) {
                log.warn("Exception in checker task", e);
            }
        }
    }

    public void stop() {
        try {
            running = false;
            thread.interrupt();
            thread.join();
        } catch (InterruptedException ignored) {
            log.warn("Interrupted while stopping", ignored);
        }
    }

    @Override
    public void onSync() {
        synced = true;
        updated();
    }

    @Override
    public void onAdd(T t) {
        updated();
    }

    private void updated() {
        if (synced) {
            synchronized (monitor) {
                log.info("Store updated, notifying");
                monitor.notifyAll();
            }
        }
    }

    @Override
    public void onUpdate(T old, T updated) {
        updated();
    }

    @Override
    public void onDelete(T t) {
        updated();
    }

    public static <T> ResourceChecker<T> create(CheckTask task, Duration recheckInterval) {
        return new ResourceChecker<>(task, recheckInterval);
    }
}
