/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */

package io.enmasse.keycloak.controller;

import io.enmasse.address.model.AddressSpace;
import io.enmasse.address.model.AuthenticationServiceType;
import io.enmasse.k8s.api.cache.Store;
import io.enmasse.k8s.api.cache.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;


public class KeycloakManager implements Watcher<AddressSpace>
{
    private static final Logger log = LoggerFactory.getLogger(KeycloakManager.class);
    private final KeycloakApi keycloak;
    private final AtomicBoolean synced = new AtomicBoolean(false);
    private final Store<AddressSpace> addressSpaceStore;

    public KeycloakManager(KeycloakApi keycloak, Store<AddressSpace> addressSpaceStore) {
        this.keycloak = keycloak;
        this.addressSpaceStore = addressSpaceStore;
    }

    @Override
    public void onSync() {
        synced.set(true);
        onChanged(addressSpaceStore.list());
    }

    @Override
    public void onAdd(AddressSpace addressSpace) {
        if (synced.get()) {
            onChanged(addressSpaceStore.list());
        }
    }

    @Override
    public void onUpdate(AddressSpace old, AddressSpace updated) {
        if (synced.get()) {
            onChanged(addressSpaceStore.list());
        }
    }

    @Override
    public void onDelete(AddressSpace addressSpace) {
        if (synced.get()) {
            onChanged(addressSpaceStore.list());
        }
    }

    void onChanged(List<AddressSpace> addressSpaces) {
        Map<String, AddressSpace> standardAuthSvcSpaces =
                addressSpaces.stream()
                             .filter(x -> x.getAuthenticationService().getType() == AuthenticationServiceType.STANDARD)
                             .collect(Collectors.toMap(AddressSpace::getName, Function.identity()));

        for(String realmName : keycloak.getRealmNames()) {
            if(standardAuthSvcSpaces.remove(realmName) == null && !"master".equals(realmName)) {
                log.info("Deleting realm {}", realmName);
                keycloak.deleteRealm(realmName);
            }
        }
        for(String name : standardAuthSvcSpaces.keySet()) {
            log.info("Creating realm {}", name);
            keycloak.createRealm(name, name + "-admin");
        }
    }
}
