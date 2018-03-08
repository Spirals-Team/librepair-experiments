/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.enmasse.address.model.v1.CodecV1;
import io.enmasse.config.LabelKeys;
import io.enmasse.address.model.AddressSpace;
import io.enmasse.k8s.api.cache.*;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.DoneableConfigMap;
import io.fabric8.openshift.client.OpenShiftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;

/**
 * Implementation of the AddressSpace API towards Kubernetes
 */
public class ConfigMapAddressSpaceApi implements AddressSpaceApi, ListerWatcher<ConfigMap> {
    protected final Logger log = LoggerFactory.getLogger(getClass().getName());
    private final OpenShiftClient client;
    private final ObjectMapper mapper = CodecV1.getMapper();

    public ConfigMapAddressSpaceApi(OpenShiftClient client) {
        this.client = client;
    }

    @Override
    public Optional<AddressSpace> getAddressSpaceWithName(String name) {
        ConfigMap map = client.configMaps().withName(name).get();
        if (map == null) {
            return Optional.empty();
        } else {
            return Optional.of(getAddressSpaceFromConfig(map));
        }
    }

    @Override
    public void createAddressSpace(AddressSpace addressSpace) throws Exception {
        try {
            create(client.configMaps().createNew(), addressSpace);
        } catch (Exception e) {
            log.error("Error creating {}", addressSpace.getName());
            throw e;
        }
    }

    @Override
    public void replaceAddressSpace(AddressSpace addressSpace) throws Exception {
        String name = addressSpace.getName();
        ConfigMap previous = client.configMaps().withName(name).get();
        if (previous == null) {
            return;
        }
        try {
            create(client.configMaps().createOrReplaceWithNew(), addressSpace);
        } catch (Exception e) {
            log.error("Error replacing {}", addressSpace.getName());
            throw e;
        }
    }

    private void create(DoneableConfigMap config, AddressSpace addressSpace) throws Exception {
        String name = addressSpace.getName();
            config.withNewMetadata()
                .withName(name)
                .addToLabels(LabelKeys.TYPE, "address-space")
                .endMetadata()
                .addToData("config.json", mapper.writeValueAsString(addressSpace))
                .done();
    }

    @Override
    public void deleteAddressSpace(AddressSpace addressSpace) {
        String name = addressSpace.getName();
        client.configMaps().withName(name).delete();
    }

    @Override
    public Set<AddressSpace> listAddressSpaces() {
        Set<AddressSpace> instances = new LinkedHashSet<>();
        for (ConfigMap map : list()) {
            instances.add(getAddressSpaceFromConfig(map));
        }
        return instances;
    }


    private AddressSpace getAddressSpaceFromConfig(ConfigMap map) {
        try {
            AddressSpace addressSpace = mapper.readValue(map.getData().get("config.json"), AddressSpace.class);
            return new AddressSpace.Builder(addressSpace).setUid(map.getMetadata().getUid()).build();
        } catch (Exception e) {
            log.error("Error decoding address space", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Watch watchAddressSpaces(Watcher<AddressSpace> watcher, Store<AddressSpace> clientState, Duration resyncInterval, int maxQueueSize) {
        SharedInformer<ConfigMap> informer = SharedInformer.create(this, new Watcher<ConfigMap>() {
            @Override
            public void onSync() {
                watcher.onSync();
            }

            @Override
            public void onAdd(ConfigMap configMap) {
                AddressSpace addressSpace = getAddressSpaceFromConfig(configMap);
                clientState.update(addressSpace);
                watcher.onAdd(addressSpace);
            }

            @Override
            public void onUpdate(ConfigMap old, ConfigMap updated) {
                AddressSpace oldAddressSpace = getAddressSpaceFromConfig(old);
                AddressSpace newAddressSpace = getAddressSpaceFromConfig(updated);
                clientState.update(newAddressSpace);
                watcher.onUpdate(oldAddressSpace, newAddressSpace);
            }

            @Override
            public void onDelete(ConfigMap configMap) {
                AddressSpace addressSpace = getAddressSpaceFromConfig(configMap);
                clientState.delete(addressSpace);
                watcher.onDelete(addressSpace);
            }
        }, resyncInterval, maxQueueSize);

        informer.start();
        return informer;
    }

    @Override
    public AddressApi withAddressSpace(AddressSpace addressSpace) {
        return new ConfigMapAddressApi(client, addressSpace.getNamespace());
    }

    @Override
    public List<ConfigMap> list() {
        return client.configMaps().withLabel(LabelKeys.TYPE, "address-space").list().getItems();
    }

    @Override
    public io.fabric8.kubernetes.client.Watch watch(io.fabric8.kubernetes.client.Watcher<ConfigMap> watcher) {
        return client.configMaps().withLabel(LabelKeys.TYPE, "address-space").watch(watcher);
    }
}
