/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api.cache;

import io.enmasse.k8s.api.ConfigMapSchemaApi;
import io.enmasse.k8s.api.Watch;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.HasMetadata;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class SharedInformerTest {
    SharedInformer<ConfigMap> informer;
    ListerWatcher<ConfigMap> testLister;
    Store<ConfigMap> testStore;
    Watcher<ConfigMap> testWatcher;

    @Before
    public void setup() {
        testLister = mock(ListerWatcher.class);
        testWatcher = mock(Watcher.class);
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        informer = SharedInformer.create(testLister, testWatcher, clock, Duration.ofSeconds(1), 6);
        testStore = informer.getClientState();
    }

    @After
    public void teardown() throws InterruptedException {
        informer.stop();
    }

    @Test
    public void testIformer() throws InterruptedException {
        when(testLister.list()).thenReturn(Arrays.asList(configMap("a1", "a2"), configMap("a1", "a3"), configMap("b1", "b2")));

        ArgumentCaptor<io.fabric8.kubernetes.client.Watcher<ConfigMap>> captor = ArgumentCaptor.forClass(io.fabric8.kubernetes.client.Watcher.class);

        informer.start();

        assertStoreSize(2);
        verify(testLister).watch(captor.capture());
        verify(testLister).list();


        assertConfigMap("a1", "a3");
        assertConfigMap("b1", "b2");

        io.fabric8.kubernetes.client.Watcher watcher = captor.getValue();
        watcher.eventReceived(io.fabric8.kubernetes.client.Watcher.Action.MODIFIED, configMap("a1", "a4"));
        assertStoreSize(2);
        assertConfigMap("a1", "a4");
        assertConfigMap("b1", "b2");
        watcher.eventReceived(io.fabric8.kubernetes.client.Watcher.Action.ADDED, configMap("c1", "c4"));
        assertStoreSize(3);
        assertConfigMap("a1", "a4");
        assertConfigMap("b1", "b2");
        assertConfigMap("c1", "c4");
        watcher.eventReceived(io.fabric8.kubernetes.client.Watcher.Action.DELETED, configMap("b1", "b2"));
        assertStoreSize(2);
        assertConfigMap("a1", "a4");
        assertConfigMap("c1", "c4");
    }

    public void assertStoreSize(long expectedSize) throws InterruptedException {
        System.out.println("WAITING FOR SIZE " + expectedSize);
        Instant endTime = Instant.now().plus(Duration.ofMinutes(1));

        long actual = testStore.list().size();
        while (expectedSize != actual && Instant.now().isBefore(endTime)) {
            Thread.sleep(100);
            actual = testStore.list().size();
        }
        assertThat(actual, is(expectedSize));
    }

    private void assertConfigMap(String name, String expectedValue) throws InterruptedException {
        System.out.println("WAITING FOR " + name + " with value " + expectedValue);
        Instant endTime = Instant.now().plus(Duration.ofMinutes(1));

        String actualValue = findValue(testStore.list(), name);

        while (!expectedValue.equals(actualValue) && Instant.now().isBefore(endTime)) {
            Thread.sleep(100);
            actualValue = findValue(testStore.list(), name);
        }
        assertNotNull("Expected configmap " + name + " not found", actualValue);
        assertThat(actualValue, is(expectedValue));
    }

    private static String findValue(List<ConfigMap> list, String name) {
        String found = null;
        for (ConfigMap map : list) {
            if (map.getMetadata().getName().equals(name)) {
                found = map.getData().get("data");
                break;
            }
        }
        return found;
    }

    private static ConfigMap configMap(String name, String data) {
        return new ConfigMapBuilder()
                .editOrNewMetadata()
                .withName(name)
                .endMetadata()
                .withData(Collections.singletonMap("data", data))
                .build();
    }
}
