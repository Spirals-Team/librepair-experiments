/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.monitor.implementation.MonitorManagementClientImpl;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import rx.Observable;

import java.util.List;


/**
 * Entry point for Monitor Metric Definitions API.
 */
public interface MetricDefinitions extends
        HasManager<MonitorManager>,
        HasInner<MonitorManagementClientImpl> {

    List<MetricDefinition> listByResource(String resourceId);

    Observable<List<MetricDefinition>> listByResourceAsync(String resourceId);
}
