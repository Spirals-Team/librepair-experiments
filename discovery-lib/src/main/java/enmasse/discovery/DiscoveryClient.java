/*
 * Copyright 2016-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */

package enmasse.discovery;

import io.enmasse.k8s.api.cache.*;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DiscoveryClient implements ListerWatcher<io.fabric8.kubernetes.api.model.Pod> {
    private final List<DiscoveryListener> listeners = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(DiscoveryClient.class.getName());
    private final String containerName;
    private final Map<String, String> labelFilter;
    private final Map<String, String> annotationFilter;
    private Set<Host> currentHosts = new LinkedHashSet<>();
    private final KubernetesClient client;
    private final SharedInformer<io.fabric8.kubernetes.api.model.Pod> informer;

    public DiscoveryClient(KubernetesClient client, Map<String, String> labelFilter, Map<String, String> annotationFilter, String containerName) {
        this.client = client;
        this.labelFilter = labelFilter;
        this.annotationFilter = annotationFilter;
        this.containerName = containerName;
        Store<io.fabric8.kubernetes.api.model.Pod> store = new ObjectStore<>(obj -> obj.getMetadata().getName());
        AtomicBoolean synced = new AtomicBoolean(false);
        this.informer = SharedInformer.create(this, store, new Watcher<io.fabric8.kubernetes.api.model.Pod>() {
            @Override
            public void onSync() {
                synced.set(true);
                resourcesUpdated(convertPods(store.list()));
            }

            @Override
            public void onAdd(io.fabric8.kubernetes.api.model.Pod pod) {
                if (synced.get()) {
                    resourcesUpdated(convertPods(store.list()));
                }
            }

            @Override
            public void onUpdate(io.fabric8.kubernetes.api.model.Pod old, io.fabric8.kubernetes.api.model.Pod updated) {
                if (synced.get()) {
                    resourcesUpdated(convertPods(store.list()));
                }
            }

            @Override
            public void onDelete(io.fabric8.kubernetes.api.model.Pod pod) {
                if (synced.get()) {
                    resourcesUpdated(convertPods(store.list()));
                }
            }
        }, Duration.ofMinutes(10), 10000);
    }

    private List<Pod> convertPods(List<io.fabric8.kubernetes.api.model.Pod> p) {
        return p.stream()
                .map(Pod::new)
                .filter(this::filterPod)
                .collect(Collectors.toList());
    }

    public DiscoveryClient(Map<String, String> labelFilter, Map<String, String> annotationFilter, String containerName) {
        this(new DefaultKubernetesClient(), labelFilter, annotationFilter, containerName);
    }

    public void addListener(DiscoveryListener listener) {
        this.listeners.add(listener);
    }

    private void notifyListeners(Set<Host> hosts) {
        if (currentHosts.equals(hosts)) {
            return;
        }
        currentHosts = new LinkedHashSet<>(hosts);
        log.debug("Received new set of hosts: " + hosts);
        for (DiscoveryListener listener : listeners) {
            listener.hostsChanged(hosts);
        }
    }

    public void start() {
        informer.start();
    }

    void resourcesUpdated(List<Pod> resources) {
        Set<Host> hosts = new HashSet<>();
        for (Pod pod : resources) {

            String host = pod.getHost();
            String ready = pod.getReady();
            String phase = pod.getPhase();
            if ("True".equals(ready) && "Running".equals(phase)) {
                Map<String, Map<String, Integer>> portMap = pod.getPortMap();
                if (containerName != null) {
                    hosts.add(new Host(host, portMap.get(containerName)));
                } else {
                    hosts.add(new Host(host, portMap.values().iterator().next()));
                }
            }
        }

        notifyListeners(hosts);
    }

    public void stop() throws InterruptedException {
        informer.stop();
    }

    private boolean filterPod(Pod pod) {
        Map<String, String> annotations = pod.getAnnotations();
        if (annotationFilter.isEmpty()) {
            return true;
        }

        if (annotations == null) {
            return false;
        }

        for (Map.Entry<String, String> filterEntry : annotationFilter.entrySet()) {
            String annotationValue = annotations.get(filterEntry.getKey());
            if (annotationValue == null || !annotationValue.equals(filterEntry.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<io.fabric8.kubernetes.api.model.Pod> list() {
        return client.pods().withLabels(labelFilter).list().getItems();
    }

    @Override
    public Watch watch(io.fabric8.kubernetes.client.Watcher<io.fabric8.kubernetes.api.model.Pod> watcher) {
        return client.pods().withLabels(labelFilter).watch(watcher);
    }
}
