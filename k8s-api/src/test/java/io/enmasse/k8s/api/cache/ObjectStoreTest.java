/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ObjectStoreTest {
    @Test
    public void testUpdate() {
        ObjectStore<String> store = new ObjectStore<>(s -> s);
        store.update("foo");
        assertThat(store.list().size(), is(1));
    }
}
