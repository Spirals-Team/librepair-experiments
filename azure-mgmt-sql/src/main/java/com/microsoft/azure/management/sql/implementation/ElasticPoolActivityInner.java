/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import org.joda.time.DateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Represents the activity on an elastic pool.
 */
@JsonFlatten
public class ElasticPoolActivityInner extends ProxyResourceInner {
    /**
     * The geo-location where the resource lives.
     */
    @JsonProperty(value = "location")
    private String location;

    /**
     * The time the operation finished (ISO8601 format).
     */
    @JsonProperty(value = "properties.endTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime endTime;

    /**
     * The error code if available.
     */
    @JsonProperty(value = "properties.errorCode", access = JsonProperty.Access.WRITE_ONLY)
    private Integer errorCode;

    /**
     * The error message if available.
     */
    @JsonProperty(value = "properties.errorMessage", access = JsonProperty.Access.WRITE_ONLY)
    private String errorMessage;

    /**
     * The error severity if available.
     */
    @JsonProperty(value = "properties.errorSeverity", access = JsonProperty.Access.WRITE_ONLY)
    private Integer errorSeverity;

    /**
     * The operation name.
     */
    @JsonProperty(value = "properties.operation", access = JsonProperty.Access.WRITE_ONLY)
    private String operation;

    /**
     * The unique operation ID.
     */
    @JsonProperty(value = "properties.operationId", access = JsonProperty.Access.WRITE_ONLY)
    private UUID operationId;

    /**
     * The percentage complete if available.
     */
    @JsonProperty(value = "properties.percentComplete", access = JsonProperty.Access.WRITE_ONLY)
    private Integer percentComplete;

    /**
     * The requested max DTU per database if available.
     */
    @JsonProperty(value = "properties.requestedDatabaseDtuMax", access = JsonProperty.Access.WRITE_ONLY)
    private Integer requestedDatabaseDtuMax;

    /**
     * The requested min DTU per database if available.
     */
    @JsonProperty(value = "properties.requestedDatabaseDtuMin", access = JsonProperty.Access.WRITE_ONLY)
    private Integer requestedDatabaseDtuMin;

    /**
     * The requested DTU for the pool if available.
     */
    @JsonProperty(value = "properties.requestedDtu", access = JsonProperty.Access.WRITE_ONLY)
    private Integer requestedDtu;

    /**
     * The requested name for the elastic pool if available.
     */
    @JsonProperty(value = "properties.requestedElasticPoolName", access = JsonProperty.Access.WRITE_ONLY)
    private String requestedElasticPoolName;

    /**
     * The requested storage limit for the pool in GB if available.
     */
    @JsonProperty(value = "properties.requestedStorageLimitInGB", access = JsonProperty.Access.WRITE_ONLY)
    private Long requestedStorageLimitInGB;

    /**
     * The name of the elastic pool.
     */
    @JsonProperty(value = "properties.elasticPoolName", access = JsonProperty.Access.WRITE_ONLY)
    private String elasticPoolName;

    /**
     * The name of the server the elastic pool is in.
     */
    @JsonProperty(value = "properties.serverName", access = JsonProperty.Access.WRITE_ONLY)
    private String serverName;

    /**
     * The time the operation started (ISO8601 format).
     */
    @JsonProperty(value = "properties.startTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime startTime;

    /**
     * The current state of the operation.
     */
    @JsonProperty(value = "properties.state", access = JsonProperty.Access.WRITE_ONLY)
    private String state;

    /**
     * The requested storage limit in MB.
     */
    @JsonProperty(value = "properties.requestedStorageLimitInMB", access = JsonProperty.Access.WRITE_ONLY)
    private Integer requestedStorageLimitInMB;

    /**
     * The requested per database DTU guarantee.
     */
    @JsonProperty(value = "properties.requestedDatabaseDtuGuarantee", access = JsonProperty.Access.WRITE_ONLY)
    private Integer requestedDatabaseDtuGuarantee;

    /**
     * The requested per database DTU cap.
     */
    @JsonProperty(value = "properties.requestedDatabaseDtuCap", access = JsonProperty.Access.WRITE_ONLY)
    private Integer requestedDatabaseDtuCap;

    /**
     * The requested DTU guarantee.
     */
    @JsonProperty(value = "properties.requestedDtuGuarantee", access = JsonProperty.Access.WRITE_ONLY)
    private Integer requestedDtuGuarantee;

    /**
     * Get the location value.
     *
     * @return the location value
     */
    public String location() {
        return this.location;
    }

    /**
     * Set the location value.
     *
     * @param location the location value to set
     * @return the ElasticPoolActivityInner object itself.
     */
    public ElasticPoolActivityInner withLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the endTime value.
     *
     * @return the endTime value
     */
    public DateTime endTime() {
        return this.endTime;
    }

    /**
     * Get the errorCode value.
     *
     * @return the errorCode value
     */
    public Integer errorCode() {
        return this.errorCode;
    }

    /**
     * Get the errorMessage value.
     *
     * @return the errorMessage value
     */
    public String errorMessage() {
        return this.errorMessage;
    }

    /**
     * Get the errorSeverity value.
     *
     * @return the errorSeverity value
     */
    public Integer errorSeverity() {
        return this.errorSeverity;
    }

    /**
     * Get the operation value.
     *
     * @return the operation value
     */
    public String operation() {
        return this.operation;
    }

    /**
     * Get the operationId value.
     *
     * @return the operationId value
     */
    public UUID operationId() {
        return this.operationId;
    }

    /**
     * Get the percentComplete value.
     *
     * @return the percentComplete value
     */
    public Integer percentComplete() {
        return this.percentComplete;
    }

    /**
     * Get the requestedDatabaseDtuMax value.
     *
     * @return the requestedDatabaseDtuMax value
     */
    public Integer requestedDatabaseDtuMax() {
        return this.requestedDatabaseDtuMax;
    }

    /**
     * Get the requestedDatabaseDtuMin value.
     *
     * @return the requestedDatabaseDtuMin value
     */
    public Integer requestedDatabaseDtuMin() {
        return this.requestedDatabaseDtuMin;
    }

    /**
     * Get the requestedDtu value.
     *
     * @return the requestedDtu value
     */
    public Integer requestedDtu() {
        return this.requestedDtu;
    }

    /**
     * Get the requestedElasticPoolName value.
     *
     * @return the requestedElasticPoolName value
     */
    public String requestedElasticPoolName() {
        return this.requestedElasticPoolName;
    }

    /**
     * Get the requestedStorageLimitInGB value.
     *
     * @return the requestedStorageLimitInGB value
     */
    public Long requestedStorageLimitInGB() {
        return this.requestedStorageLimitInGB;
    }

    /**
     * Get the elasticPoolName value.
     *
     * @return the elasticPoolName value
     */
    public String elasticPoolName() {
        return this.elasticPoolName;
    }

    /**
     * Get the serverName value.
     *
     * @return the serverName value
     */
    public String serverName() {
        return this.serverName;
    }

    /**
     * Get the startTime value.
     *
     * @return the startTime value
     */
    public DateTime startTime() {
        return this.startTime;
    }

    /**
     * Get the state value.
     *
     * @return the state value
     */
    public String state() {
        return this.state;
    }

    /**
     * Get the requestedStorageLimitInMB value.
     *
     * @return the requestedStorageLimitInMB value
     */
    public Integer requestedStorageLimitInMB() {
        return this.requestedStorageLimitInMB;
    }

    /**
     * Get the requestedDatabaseDtuGuarantee value.
     *
     * @return the requestedDatabaseDtuGuarantee value
     */
    public Integer requestedDatabaseDtuGuarantee() {
        return this.requestedDatabaseDtuGuarantee;
    }

    /**
     * Get the requestedDatabaseDtuCap value.
     *
     * @return the requestedDatabaseDtuCap value
     */
    public Integer requestedDatabaseDtuCap() {
        return this.requestedDatabaseDtuCap;
    }

    /**
     * Get the requestedDtuGuarantee value.
     *
     * @return the requestedDtuGuarantee value
     */
    public Integer requestedDtuGuarantee() {
        return this.requestedDtuGuarantee;
    }

}
