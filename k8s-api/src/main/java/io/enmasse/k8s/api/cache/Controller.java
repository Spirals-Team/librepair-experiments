/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api.cache;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Controller<T extends HasMetadata> implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Controller.class.getName());
    private final Duration resyncInterval;
    private final ListerWatcher<T> listerWatcher;
    private final Processor<Event<T>> processor;
    private final WorkQueue<T> queue;
    private final Clock clock;
    private Watch watch;
    private volatile Instant nextResync = Instant.MIN;
    private volatile boolean running;

    public Controller(Config<T> config) {
        this.resyncInterval = config.resyncInterval;
        this.listerWatcher = config.listerWatcher;
        this.processor = config.processor;
        this.queue = config.queue;
        this.clock = config.clock;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Instant now = Instant.now(clock);
                if (now.isAfter(nextResync)) {
                    log.info("Doing resync");
                    resync();
                }
                long sleepTime = nextResync.getEpochSecond() - now.getEpochSecond();
                queue.pop(processor, sleepTime, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.interrupted();
            } catch (Exception e) {
                log.warn("Exception doing resource update", e);
            }
        }
    }

    private void resync() {
        if (watch != null) {
            watch.close();
        }
        for (T t : listerWatcher.list()) {
            queue.add(new Event<>(EventType.ADDED, t));
        }
        queue.add(new Event<>(EventType.SYNC, null));
        watch = listerWatcher.watch(new Watcher<T>() {
            @Override
            public void eventReceived(Action action, T t) {
                switch (action) {
                    case ADDED:
                        queue.add(new Event<>(EventType.ADDED, t));
                        log.info("Added event {}", t.getMetadata().getName());
                        break;
                    case DELETED:
                        log.info("Deleted event {}", t.getMetadata().getName());
                        queue.add(new Event<>(EventType.DELETED, t));
                        break;
                    case MODIFIED:
                        log.info("Modified event {}", t.getMetadata().getName());
                        queue.add(new Event<>(EventType.UPDATED, t));
                        break;
                    case ERROR:
                        log.error("Got error event while watching resource " + t);
                        break;
                }
            }

            @Override
            public void onClose(KubernetesClientException cause) {

                if (cause != null) {
                    log.info("Received onClose", cause);
                    if (watch != null) {
                        watch.close();
                        watch = null;
                    }
                    nextResync = Instant.MIN;
                } else {
                    log.info("Watch for address space configs force closed, stopping");
                    watch = null;
                }
            }
        });

        nextResync = Instant.now(clock).plus(resyncInterval);
    }

    public void shutdown() {
        running = false;
    }

    public static class Config<T> {
        private Clock clock;
        private Duration resyncInterval;
        private ListerWatcher<T> listerWatcher;
        private Processor<Event<T>> processor;
        private WorkQueue<T> queue;

        public Config<T> setClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public Config<T> setResyncInterval(Duration resyncInterval) {
            this.resyncInterval = resyncInterval;
            return this;
        }

        public Config<T> setListerWatcher(ListerWatcher<T> listerWatcher) {
            this.listerWatcher = listerWatcher;
            return this;
        }

        public Config<T> setProcessor(Processor<Event<T>> processor) {
            this.processor = processor;
            return this;
        }

        public Config<T> setWorkQueue(WorkQueue<T> queue) {
            this.queue = queue;
            return this;
        }
    }
}
