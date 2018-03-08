/*
 * Copyright 2017-2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api;

import io.enmasse.k8s.api.cache.ObjectStore;
import io.enmasse.k8s.api.cache.Store;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Duration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ResourceCheckerTest {
    ResourceChecker<String> controller;
    CheckTask checkTask;

    @Before
    public void setup() {
        checkTask = Mockito.mock(CheckTask.class);

        controller = ResourceChecker.create(checkTask, Duration.ofMillis(1));
    }

    @Test
    public void testResourcesAdded() throws Exception {
        controller.onAdd("hello");
        controller.onAdd("there");
        controller.doWork();
        verify(checkTask).check();
    }

    @Test
    public void testResourcesRemoved() throws Exception {
        controller.onAdd("there");
        controller.doWork();
        controller.onDelete("there");
        controller.doWork();
        verify(checkTask, times(2)).check();
    }

    @Test
    public void testResourcesUpdated() throws Exception {
        controller.onAdd("there");
        controller.doWork();
        controller.onUpdate("there", "there again");
        controller.doWork();
        verify(checkTask, times(2)).check();
    }
}
