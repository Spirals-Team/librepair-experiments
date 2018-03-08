/*
 * Copyright 2016-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.controller.standard;

import io.enmasse.address.model.Schema;
import io.enmasse.k8s.api.cache.Store;
import io.enmasse.k8s.api.cache.Watcher;

import java.util.List;

public class CachingSchemaProvider implements SchemaProvider {
    private final Store<Schema> schemaStore;

    public CachingSchemaProvider(Store<Schema> schemaStore) {
        this.schemaStore = schemaStore;
    }

    @Override
    public Schema getSchema() {
        List<Schema> schemaList = schemaStore.list();
        if (schemaList.isEmpty()) {
            return null;
        }
        return schemaList.get(0);
    }
}
