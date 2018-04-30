/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.implementation;

import com.microsoft.azure.management.apimanagement.LoggerType;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * Logger details.
 */
@JsonFlatten
public class LoggerContractInner extends Resource {
    /**
     * Logger type. Possible values include: 'azureEventHub',
     * 'applicationInsights'.
     */
    @JsonProperty(value = "properties.loggerType", required = true)
    private LoggerType loggerType;

    /**
     * Logger description.
     */
    @JsonProperty(value = "properties.description")
    private String description;

    /**
     * The name and SendRule connection string of the event hub for
     * azureEventHub logger.
     * Instrumentation key for applicationInsights logger.
     */
    @JsonProperty(value = "properties.credentials", required = true)
    private Map<String, String> credentials;

    /**
     * Whether records are buffered in the logger before publishing. Default is
     * assumed to be true.
     */
    @JsonProperty(value = "properties.isBuffered")
    private Boolean isBuffered;

    /**
     * Get the loggerType value.
     *
     * @return the loggerType value
     */
    public LoggerType loggerType() {
        return this.loggerType;
    }

    /**
     * Set the loggerType value.
     *
     * @param loggerType the loggerType value to set
     * @return the LoggerContractInner object itself.
     */
    public LoggerContractInner withLoggerType(LoggerType loggerType) {
        this.loggerType = loggerType;
        return this;
    }

    /**
     * Get the description value.
     *
     * @return the description value
     */
    public String description() {
        return this.description;
    }

    /**
     * Set the description value.
     *
     * @param description the description value to set
     * @return the LoggerContractInner object itself.
     */
    public LoggerContractInner withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the credentials value.
     *
     * @return the credentials value
     */
    public Map<String, String> credentials() {
        return this.credentials;
    }

    /**
     * Set the credentials value.
     *
     * @param credentials the credentials value to set
     * @return the LoggerContractInner object itself.
     */
    public LoggerContractInner withCredentials(Map<String, String> credentials) {
        this.credentials = credentials;
        return this;
    }

    /**
     * Get the isBuffered value.
     *
     * @return the isBuffered value
     */
    public Boolean isBuffered() {
        return this.isBuffered;
    }

    /**
     * Set the isBuffered value.
     *
     * @param isBuffered the isBuffered value to set
     * @return the LoggerContractInner object itself.
     */
    public LoggerContractInner withIsBuffered(Boolean isBuffered) {
        this.isBuffered = isBuffered;
        return this;
    }

}
