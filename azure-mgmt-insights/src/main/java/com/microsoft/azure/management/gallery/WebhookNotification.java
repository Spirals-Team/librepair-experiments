/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.gallery;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Webhook notification of an autoscale event.
 */
public class WebhookNotification {
    /**
     * the service address to receive the notification.
     */
    @JsonProperty(value = "serviceUri")
    private String serviceUri;

    /**
     * a property bag of settings. This value can be empty.
     */
    @JsonProperty(value = "properties")
    private Map<String, String> properties;

    /**
     * Get the serviceUri value.
     *
     * @return the serviceUri value
     */
    public String serviceUri() {
        return this.serviceUri;
    }

    /**
     * Set the serviceUri value.
     *
     * @param serviceUri the serviceUri value to set
     * @return the WebhookNotification object itself.
     */
    public WebhookNotification withServiceUri(String serviceUri) {
        this.serviceUri = serviceUri;
        return this;
    }

    /**
     * Get the properties value.
     *
     * @return the properties value
     */
    public Map<String, String> properties() {
        return this.properties;
    }

    /**
     * Set the properties value.
     *
     * @param properties the properties value to set
     * @return the WebhookNotification object itself.
     */
    public WebhookNotification withProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

}
