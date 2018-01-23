/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.monitor.implementation.MonitorManagementClientImpl;
import com.microsoft.azure.management.monitor.implementation.MonitorManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import org.joda.time.DateTime;
import rx.Observable;

/**
 * Entry point for Monitor Activity logs API.
 */
public interface ActivityLogs extends
        HasManager<MonitorManager>,
        HasInner<MonitorManagementClientImpl> {

    FilterDefinitionStages.WithStartTimeFilter  defineQuery();

    /**
     * The entirety of a Activity Logs query definition.
     */
    interface Definition extends
            FilterDefinitionStages.WithStartTimeFilter,
            FilterDefinitionStages.WithEndFilter,
            FilterDefinitionStages.WithFieldFilter,
            FilterDefinitionStages.WithResponsePropertyDefinition,
            FilterDefinitionStages.WithSelect,
            FilterDefinitionStages.WithExecute {
    }

    interface FilterDefinitionStages {

        interface WithStartTimeFilter {
            WithEndFilter withStartTime(DateTime startTime);
        }

        interface WithEndFilter {
            WithFieldFilter withEndTime(DateTime endTime);
        }

        interface WithFieldFilter {
            WithResponsePropertyDefinition defineResponseProperties();
            WithSelect withAllPropertiesInResponse();
        }

        interface WithResponsePropertyDefinition {
            WithResponsePropertyDefinition withAuthorization();
            WithResponsePropertyDefinition withClaims();
            WithResponsePropertyDefinition withCorrelationId();
            WithResponsePropertyDefinition withDescription();
            WithResponsePropertyDefinition withEventDataId();
            WithResponsePropertyDefinition withEventName();
            WithResponsePropertyDefinition withEventTimestamp();
            WithResponsePropertyDefinition withHttpRequest();
            WithResponsePropertyDefinition withLevel();
            WithResponsePropertyDefinition withOperationId();
            WithResponsePropertyDefinition withOperationName();
            WithResponsePropertyDefinition withProperties();
            WithResponsePropertyDefinition withResourceGroupName();
            WithResponsePropertyDefinition withResourceProviderName();
            WithResponsePropertyDefinition withResourceId();
            WithResponsePropertyDefinition withStatus();
            WithResponsePropertyDefinition withSubmissionTimestamp();
            WithResponsePropertyDefinition withSubStatus();
            WithResponsePropertyDefinition withSubscriptionId();
            WithSelect apply();
        }

        interface WithSelect extends
                WithExecute {

            WithExecute filterByResourceGroup(String resourceGroupName);

            WithExecute filterByResource(String resourceId);

            WithExecute filterByResourceProvider(String resourceProviderName);

            WithExecute filterByCorrelationId(String correlationId);
        }

        interface WithExecute {
            PagedList<EventData> execute();

            Observable<EventData> executeAsync();
        }
    }
}