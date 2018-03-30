/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.Period;

import java.util.UUID;

/**
 * the detailed information for a vertex.
 */
public class JobStatisticsVertex {
    /**
     * the name of the vertex.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    /**
     * the id of the vertex.
     */
    @JsonProperty(value = "vertexId", access = JsonProperty.Access.WRITE_ONLY)
    private UUID vertexId;

    /**
     * the amount of execution time of the vertex.
     */
    @JsonProperty(value = "executionTime", access = JsonProperty.Access.WRITE_ONLY)
    private Period executionTime;

    /**
     * the amount of data read of the vertex, in bytes.
     */
    @JsonProperty(value = "dataRead", access = JsonProperty.Access.WRITE_ONLY)
    private Long dataRead;

    /**
     * the amount of peak memory usage of the vertex, in bytes.
     */
    @JsonProperty(value = "peakMemUsage", access = JsonProperty.Access.WRITE_ONLY)
    private Long peakMemUsage;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Get the vertexId value.
     *
     * @return the vertexId value
     */
    public UUID vertexId() {
        return this.vertexId;
    }

    /**
     * Get the executionTime value.
     *
     * @return the executionTime value
     */
    public Period executionTime() {
        return this.executionTime;
    }

    /**
     * Get the dataRead value.
     *
     * @return the dataRead value
     */
    public Long dataRead() {
        return this.dataRead;
    }

    /**
     * Get the peakMemUsage value.
     *
     * @return the peakMemUsage value
     */
    public Long peakMemUsage() {
        return this.peakMemUsage;
    }

}
