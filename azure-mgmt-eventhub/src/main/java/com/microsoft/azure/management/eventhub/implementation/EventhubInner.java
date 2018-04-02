/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.eventhub.implementation;

import java.util.List;
import org.joda.time.DateTime;
import com.microsoft.azure.management.eventhub.EntityStatus;
import com.microsoft.azure.management.eventhub.CaptureDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Single item in List or Get Event Hub operation.
 */
@JsonFlatten
public class EventhubInner extends NestedResourceInner {
    /**
     * Current number of shards on the Event Hub.
     */
    @JsonProperty(value = "properties.partitionIds", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> partitionIds;

    /**
     * Exact time the Event Hub was created.
     */
    @JsonProperty(value = "properties.createdAt", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime createdAt;

    /**
     * The exact time the message was updated.
     */
    @JsonProperty(value = "properties.updatedAt", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime updatedAt;

    /**
     * Number of days to retain the events for this Event Hub, value should be
     * 1 to 7 days.
     */
    @JsonProperty(value = "properties.messageRetentionInDays")
    private Long messageRetentionInDays;

    /**
     * Number of partitions created for the Event Hub, allowed values are from
     * 1 to 32 partitions.
     */
    @JsonProperty(value = "properties.partitionCount")
    private Long partitionCount;

    /**
     * Enumerates the possible values for the status of the Event Hub. Possible
     * values include: 'Active', 'Disabled', 'Restoring', 'SendDisabled',
     * 'ReceiveDisabled', 'Creating', 'Deleting', 'Renaming', 'Unknown'.
     */
    @JsonProperty(value = "properties.status")
    private EntityStatus status;

    /**
     * Properties of capture description.
     */
    @JsonProperty(value = "properties.captureDescription")
    private CaptureDescription captureDescription;

    /**
     * Get the partitionIds value.
     *
     * @return the partitionIds value
     */
    public List<String> partitionIds() {
        return this.partitionIds;
    }

    /**
     * Get the createdAt value.
     *
     * @return the createdAt value
     */
    public DateTime createdAt() {
        return this.createdAt;
    }

    /**
     * Get the updatedAt value.
     *
     * @return the updatedAt value
     */
    public DateTime updatedAt() {
        return this.updatedAt;
    }

    /**
     * Get the messageRetentionInDays value.
     *
     * @return the messageRetentionInDays value
     */
    public Long messageRetentionInDays() {
        return this.messageRetentionInDays;
    }

    /**
     * Set the messageRetentionInDays value.
     *
     * @param messageRetentionInDays the messageRetentionInDays value to set
     * @return the EventhubInner object itself.
     */
    public EventhubInner withMessageRetentionInDays(Long messageRetentionInDays) {
        this.messageRetentionInDays = messageRetentionInDays;
        return this;
    }

    /**
     * Get the partitionCount value.
     *
     * @return the partitionCount value
     */
    public Long partitionCount() {
        return this.partitionCount;
    }

    /**
     * Set the partitionCount value.
     *
     * @param partitionCount the partitionCount value to set
     * @return the EventhubInner object itself.
     */
    public EventhubInner withPartitionCount(Long partitionCount) {
        this.partitionCount = partitionCount;
        return this;
    }

    /**
     * Get the status value.
     *
     * @return the status value
     */
    public EntityStatus status() {
        return this.status;
    }

    /**
     * Set the status value.
     *
     * @param status the status value to set
     * @return the EventhubInner object itself.
     */
    public EventhubInner withStatus(EntityStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Get the captureDescription value.
     *
     * @return the captureDescription value
     */
    public CaptureDescription captureDescription() {
        return this.captureDescription;
    }

    /**
     * Set the captureDescription value.
     *
     * @param captureDescription the captureDescription value to set
     * @return the EventhubInner object itself.
     */
    public EventhubInner withCaptureDescription(CaptureDescription captureDescription) {
        this.captureDescription = captureDescription;
        return this;
    }

}
