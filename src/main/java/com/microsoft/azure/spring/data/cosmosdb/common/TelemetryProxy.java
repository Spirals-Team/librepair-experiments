/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.cosmosdb.common;

import com.microsoft.applicationinsights.TelemetryClient;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

public class TelemetryProxy {

    private TelemetryClient client;
    private boolean isTelemetryAllowed;
    private static final String PROJECT_INFO = "spring-data-cosmosdb/" + PropertyLoader.getProjectVersion();

    public TelemetryProxy(boolean isTelemetryAllowed) {
        this.client = new TelemetryClient();
        this.isTelemetryAllowed = isTelemetryAllowed;
    }

    public void trackEvent(@NonNull String name) {
        this.trackEvent(name, null, false);
    }

    public void trackEvent(@NonNull String name, @NonNull Map<String, String> customProperties) {
        this.trackEvent(name, customProperties, false);
    }

    public void trackEvent(@NonNull String name, @NonNull Map<String, String> customProperties,
                           boolean isOverrideDefault) {
        if (this.isTelemetryAllowed) {
            Map<String, String> properties = this.getDefaultProperties();
            properties = this.mergeProperties(properties, customProperties, isOverrideDefault);
            client.trackEvent(name, properties, null);
            client.flush();
        }
    }

    private Map<String, String> mergeProperties(@NonNull Map<String, String> defaultProperties,
                                                @Nullable Map<String, String> customProperties,
                                                boolean isOverrideDefault) {
        if (customProperties == null || customProperties.isEmpty()) {
            return defaultProperties;
        }

        final Map<String, String> mergedProperties = new HashMap<>();

        if (isOverrideDefault) {
            mergedProperties.putAll(defaultProperties);
            mergedProperties.putAll(customProperties);
        } else {
            mergedProperties.putAll(customProperties);
            mergedProperties.putAll(defaultProperties);
        }

        mergedProperties.forEach((key, value) -> {
            if (value.isEmpty()) {
                mergedProperties.remove(key);
            }
        });

        return mergedProperties;
    }

    private Map<String, String> getDefaultProperties() {
        final Map<String, String> properties = new HashMap<>();

        properties.put(TelemetryProperties.PROPERTY_INSTALLATION_ID, GetHashMac.getHashMac());
        properties.put(TelemetryProperties.PROPERTY_VERSION, PROJECT_INFO);

        return properties;
    }

    public void trackCustomEvent(Class<?> clazz) {
        final Map<String, String> customProperties = new HashMap<>();

        customProperties.put(TelemetryProperties.PROPERTY_SERVICE_NAME, "cosmosdb");

        this.trackEvent(ClassUtils.getUserClass(clazz).getSimpleName(), customProperties);
    }
}

