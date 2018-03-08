/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.enmasse.address.model.v1.CodecV1;
import io.enmasse.config.LabelKeys;
import io.enmasse.config.AnnotationKeys;
import io.enmasse.address.model.Address;
import io.enmasse.k8s.api.cache.*;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ConfigMapList;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;

/**
 * Implements the AddressApi using config maps.
 */
public class ConfigMapAddressApi implements AddressApi, ListerWatcher<ConfigMap> {

    private static final Logger log = LoggerFactory.getLogger(ConfigMapAddressApi.class);
    private final KubernetesClient client;
    private final String namespace;

    private final ObjectMapper mapper = CodecV1.getMapper();

    public ConfigMapAddressApi(KubernetesClient client, String namespace) {
        this.client = client;
        this.namespace = namespace;
    }

    @Override
    public Optional<Address> getAddressWithName(String name) {
        ConfigMap map = client.configMaps().inNamespace(namespace).withName(name).get();
        if (map == null) {
            return Optional.empty();
        } else {
            return Optional.of(getAddressFromConfig(map));
        }
    }

    @Override
    public Optional<Address> getAddressWithUuid(String uuid) {
        Map<String, String> labels = new LinkedHashMap<>();
        labels.put(LabelKeys.TYPE, "address-config");
        labels.put(LabelKeys.UUID, uuid);

        ConfigMapList list = client.configMaps().inNamespace(namespace).withLabels(labels).list();
        if (list.getItems().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(getAddressFromConfig(list.getItems().get(0)));
        }
    }

    @SuppressWarnings("unchecked")
    private Address getAddressFromConfig(ConfigMap configMap) {
        Map<String, String> data = configMap.getData();

        try {
            Address.Builder builder = new Address.Builder(mapper.readValue(data.get("config.json"), Address.class));
            builder.setVersion(configMap.getMetadata().getResourceVersion());
            return builder.build();
        } catch (Exception e) {
            log.warn("Unable to decode address", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Address> listAddresses() {
        Map<String, String> labels = new LinkedHashMap<>();
        labels.put(LabelKeys.TYPE, "address-config");

        Set<Address> addresses = new LinkedHashSet<>();
        ConfigMapList list = client.configMaps().inNamespace(namespace).withLabels(labels).list();
        for (ConfigMap config : list.getItems()) {
            addresses.add(getAddressFromConfig(config));
        }
        return addresses;
    }

    @Override
    public void createAddress(Address address) {
        String name = address.getName();
        ConfigMap map = create(address);
        if (map != null) {
            client.configMaps().inNamespace(namespace).withName(name).create(map);
        }
    }

    @Override
    public void replaceAddress(Address address) {
        String name = address.getName();
        ConfigMap previous = client.configMaps().inNamespace(namespace).withName(name).get();
        if (previous == null) {
            return;
        }
        ConfigMap newMap = create(address);
        if (newMap != null) {
            client.configMaps().inNamespace(namespace).withName(name).replace(newMap);
        }
    }

    private ConfigMap create(Address address) {
        String name = address.getName();
        ConfigMapBuilder builder = new ConfigMapBuilder()
                .editOrNewMetadata()
                .withName(name)
                .addToLabels(LabelKeys.TYPE, "address-config")
                // TODO: Support other ways of doing this
                .addToAnnotations(AnnotationKeys.CLUSTER_ID, name)
                .addToAnnotations(AnnotationKeys.ADDRESS_SPACE, address.getAddressSpace())
                .endMetadata();

        if (address.getVersion() != null) {
            builder.editOrNewMetadata()
                    .withResourceVersion(address.getVersion());
        }

        try {
            builder.addToData("config.json", mapper.writeValueAsString(address));
            return builder.build();
        } catch (Exception e) {
            log.info("Error serializing address for {}", address, e);
            return null;
        }
    }

    @Override
    public void deleteAddress(Address address) {
        String name = address.getName();
        client.configMaps().inNamespace(namespace).withName(name).delete();
    }

    @Override
    public Watch watchAddresses(Watcher<Address> eventHandler, Store<Address> addressStore, Duration resyncInterval, int maxQueueSize) {
        SharedInformer<ConfigMap> informer = SharedInformer.create(this, new Watcher<ConfigMap>() {
            @Override
            public void onSync() {
                eventHandler.onSync();
            }

            @Override
            public void onAdd(ConfigMap configMap) {
                Address address = getAddressFromConfig(configMap);
                addressStore.update(address);
                eventHandler.onAdd(address);
            }

            @Override
            public void onUpdate(ConfigMap old, ConfigMap updated) {
                Address oldAddress = old != null ? getAddressFromConfig(old) : null;
                Address address = getAddressFromConfig(updated);
                addressStore.update(address);
                eventHandler.onUpdate(oldAddress, address);
            }

            @Override
            public void onDelete(ConfigMap configMap) {
                Address address = getAddressFromConfig(configMap);
                addressStore.delete(address);
                eventHandler.onDelete(getAddressFromConfig(configMap));
            }
        }, resyncInterval, maxQueueSize);

        informer.start();
        return informer;
    }

    @Override
    public List<ConfigMap> list() {
        return client.configMaps().inNamespace(namespace).withLabel(LabelKeys.TYPE, "address-config").list().getItems();
    }

    @Override
    public io.fabric8.kubernetes.client.Watch watch(io.fabric8.kubernetes.client.Watcher<ConfigMap> watcher) {
        return client.configMaps().inNamespace(namespace).withLabel(LabelKeys.TYPE, "address-config").watch(watcher);
    }
}
