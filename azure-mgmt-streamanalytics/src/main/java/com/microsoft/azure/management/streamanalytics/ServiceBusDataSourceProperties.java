/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.streamanalytics;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The common properties that are associated with Service Bus data sources
 * (Queues, Topics, Event Hubs, etc.).
 */
public class ServiceBusDataSourceProperties {
    /**
     * The namespace that is associated with the desired Event Hub, Service Bus
     * Queue, Service Bus Topic, etc. Required on PUT (CreateOrReplace)
     * requests.
     */
    @JsonProperty(value = "serviceBusNamespace")
    private String serviceBusNamespace;

    /**
     * The shared access policy name for the Event Hub, Service Bus Queue,
     * Service Bus Topic, etc. Required on PUT (CreateOrReplace) requests.
     */
    @JsonProperty(value = "sharedAccessPolicyName")
    private String sharedAccessPolicyName;

    /**
     * The shared access policy key for the specified shared access policy.
     * Required on PUT (CreateOrReplace) requests.
     */
    @JsonProperty(value = "sharedAccessPolicyKey")
    private String sharedAccessPolicyKey;

    /**
     * Get the serviceBusNamespace value.
     *
     * @return the serviceBusNamespace value
     */
    public String serviceBusNamespace() {
        return this.serviceBusNamespace;
    }

    /**
     * Set the serviceBusNamespace value.
     *
     * @param serviceBusNamespace the serviceBusNamespace value to set
     * @return the ServiceBusDataSourceProperties object itself.
     */
    public ServiceBusDataSourceProperties withServiceBusNamespace(String serviceBusNamespace) {
        this.serviceBusNamespace = serviceBusNamespace;
        return this;
    }

    /**
     * Get the sharedAccessPolicyName value.
     *
     * @return the sharedAccessPolicyName value
     */
    public String sharedAccessPolicyName() {
        return this.sharedAccessPolicyName;
    }

    /**
     * Set the sharedAccessPolicyName value.
     *
     * @param sharedAccessPolicyName the sharedAccessPolicyName value to set
     * @return the ServiceBusDataSourceProperties object itself.
     */
    public ServiceBusDataSourceProperties withSharedAccessPolicyName(String sharedAccessPolicyName) {
        this.sharedAccessPolicyName = sharedAccessPolicyName;
        return this;
    }

    /**
     * Get the sharedAccessPolicyKey value.
     *
     * @return the sharedAccessPolicyKey value
     */
    public String sharedAccessPolicyKey() {
        return this.sharedAccessPolicyKey;
    }

    /**
     * Set the sharedAccessPolicyKey value.
     *
     * @param sharedAccessPolicyKey the sharedAccessPolicyKey value to set
     * @return the ServiceBusDataSourceProperties object itself.
     */
    public ServiceBusDataSourceProperties withSharedAccessPolicyKey(String sharedAccessPolicyKey) {
        this.sharedAccessPolicyKey = sharedAccessPolicyKey;
        return this;
    }

}
